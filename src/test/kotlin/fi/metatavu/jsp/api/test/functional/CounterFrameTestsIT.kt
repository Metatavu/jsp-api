package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Tests for counter frames
 */
class CounterFrameTestsIT: AbstractFunctionalTest() {
    @Test
    fun testFindCounterFrame() {
        TestBuilder().use { testBuilder ->
            val createdOrder = testBuilder.admin().orders().create()
            val foundCounterFrame = testBuilder.admin().counterFrames().find(createdOrder.counterFrame.id!!)
            testBuilder.admin().counterFrames().assertCounterFramesEqual(createdOrder.counterFrame, foundCounterFrame)

            testBuilder.admin().counterFrames().assertFindFailsStatus(UUID.randomUUID(), 404)
        }
    }

    @Test
    fun testListCounterFrames() {
        TestBuilder().use { testBuilder ->
            testBuilder.admin().orders().create()
            assertEquals(1, testBuilder.admin().counterFrames().list().size)

            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()

            assertEquals(5, testBuilder.admin().counterFrames().list().size)
        }
    }
}