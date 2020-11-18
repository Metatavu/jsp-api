package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Test
import org.junit.Assert.assertEquals
import java.util.*

class DrawersTestIT {

    @Test
    fun testListDrawers() {
        TestBuilder().use { testBuilder ->
            testBuilder.admin().orders().create()
            assertEquals(1, testBuilder.admin().drawers().list().size)

            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()

            assertEquals(5, testBuilder.admin().drawers().list().size)
        }
    }

    @Test
    fun testFindDrawers() {
        TestBuilder().use { testBuilder ->
            val createdDrawers = testBuilder.admin().orders().create().drawersInfo
            val foundDrawers = testBuilder.admin().drawers().find(createdDrawers.id!!)
            testBuilder.admin().drawers().assertDrawersEqual(createdDrawers, foundDrawers)

            testBuilder.admin().drawers().assertFindFailStatus(UUID.randomUUID(), 404)
        }
    }
}