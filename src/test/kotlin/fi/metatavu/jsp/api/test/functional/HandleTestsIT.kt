package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class HandleTestsIT {
    @Test
    fun testListHandles() {
        TestBuilder().use {testBuilder ->
            testBuilder.admin().orders().create()
            assertEquals(testBuilder.admin().handles().list().size, 1)

            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()

            assertEquals(testBuilder.admin().handles().list().size, 5)
        }
    }

    @Test
    fun testFindHandle() {
        TestBuilder().use {testBuilder ->
            val createdHandle = testBuilder.admin().orders().create().handles[0]
            val foundHandle = testBuilder.admin().handles().find(createdHandle.id!!)
            testBuilder.admin().handles().assertHandlesEqual(createdHandle, foundHandle)

            testBuilder.admin().handles().assertFindFailStatus(UUID.randomUUID(), 404)
        }
    }
}