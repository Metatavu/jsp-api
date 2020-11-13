package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DoorTestIt {

    @Test
    fun testListDoors() {
        TestBuilder().use { testBuilder ->
            testBuilder.admin().orders().create()
            assertEquals(1, testBuilder.admin().doors().list().size)

            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()

            assertEquals(5, testBuilder.admin().doors().list().size)
        }
    }

    @Test
    fun testFindDoor() {
        TestBuilder().use { testBuilder ->
            val createdDoor = testBuilder.admin().orders().create().doors[0]
            val foundDoor = testBuilder.admin().doors().find(createdDoor.id!!)
            testBuilder.admin().doors().assertDoorsEqual(createdDoor, foundDoor)

            testBuilder.admin().doors().assertFindFailStatus(UUID.randomUUID(), 404)
        }
    }
}
