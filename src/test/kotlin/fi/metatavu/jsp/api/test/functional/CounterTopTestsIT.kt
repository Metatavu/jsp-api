package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Tests for counter tops
 */
class CounterTopTestsIT: AbstractFunctionalTest() {
    @Test
    fun testListCounterTops() {
        TestBuilder().use { testBuilder ->
            testBuilder.admin().orders().create()
            assertEquals(1, testBuilder.admin().counterTops().list().size)

            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()

            assertEquals(5, testBuilder.admin().counterTops().list().size)
        }
    }

    @Test
    fun testFindCounterTop() {
        TestBuilder().use { testBuilder ->
            val createdCounterTop = testBuilder.admin().orders().create().counterTops[0]
            val foundCounterTop = testBuilder.admin().counterTops().find(createdCounterTop.id!!)
            testBuilder.admin().counterTops().assertCounterTopsEqual(createdCounterTop, foundCounterTop)
            testBuilder.admin().counterTops().assertFindFailStatus(UUID.randomUUID(), 404)
        }
    }

}