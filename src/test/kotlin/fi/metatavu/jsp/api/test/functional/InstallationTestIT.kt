package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Test
import org.junit.Assert.assertEquals
import java.util.*

class InstallationTestIT {

    @Test
    fun testFindInstallation() {
        TestBuilder().use { testBuilder ->
            val createdOrder = testBuilder.admin().orders().create()
            val foundInstallation = testBuilder.admin().installations().find(createdOrder.installation.id!!)
            testBuilder.admin().installations().assertInstallationsEqual(createdOrder.installation, foundInstallation)
            testBuilder.admin().installations().assertFindFailStatus(UUID.randomUUID(), 404)
        }
    }

    @Test
    fun testListInstallations() {
        TestBuilder().use { testBuilder ->
            testBuilder.admin().orders().create()
            assertEquals(1, testBuilder.admin().installations().list().size)

            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()

            assertEquals(5, testBuilder.admin().installations().list().size)
        }
    }
}