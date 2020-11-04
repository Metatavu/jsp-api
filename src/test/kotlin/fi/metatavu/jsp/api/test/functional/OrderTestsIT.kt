package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.client.models.*
import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.OffsetDateTime

class OrderTestsIT: AbstractFunctionalTest() {
    @Test
    fun createOrderTest() {
        TestBuilder().use { testBuilder ->
            val testOrder = constructTestOrder()
            val createdOrder = testBuilder.admin().orders().create(testOrder)

            assertEquals(testOrder.orderInfo!!.customer, createdOrder.orderInfo!!.customer)
            assertEquals(testOrder.orderInfo!!.email, createdOrder.orderInfo!!.email)
            assertEquals(testOrder.orderInfo!!.deliveryAddress, createdOrder.orderInfo!!.deliveryAddress)
            assertEquals(testOrder.orderInfo!!.phoneNumber, createdOrder.orderInfo!!.phoneNumber)
            assertEquals(testOrder.orderInfo!!.city, createdOrder.orderInfo!!.city)
            assertEquals(testOrder.orderInfo!!.socialMediaPermission, createdOrder.orderInfo!!.socialMediaPermission)
            assertEquals(testOrder.orderInfo!!.room, createdOrder.orderInfo!!.room)
            assertEquals(testOrder.orderInfo!!.deliveryTime.split(".")[0], createdOrder.orderInfo!!.deliveryTime.split(".")[0])
            assertEquals(testOrder.orderInfo!!.additionalInformation, createdOrder.orderInfo!!.additionalInformation)
            assertEquals(testOrder.moreInformation, createdOrder.moreInformation)
            assertEquals(testOrder.exceptionalNotes!![0], createdOrder.exceptionalNotes!![0])
        }
    }

    @Test
    fun updateOrderTest() {
        TestBuilder().use { testBuilder ->
            val testOrder = constructTestOrder()
            val createdOrder = testBuilder.admin().orders().create(testOrder)

            val testDate = OffsetDateTime.now().plusDays(23).toString().replace("+02:00", "Z").replace("+03:00", "Z")
            val orderInfo2 = OrderInfo(
                    "Asiakas Matti",
                    "matti@matti.matti",
                    "Hallituskatu 712",
                    "222222",
                    "Otava",
                    true,
                    "32",
                    testDate,
                    "ABCD")

            val exceptionalNotes = ArrayList<String>()
            exceptionalNotes.add("Updated notes")

            val orderToUpdate = Order(createdOrder.id,
                    orderInfo2,
                    null,
                    null,
                    null,
                    exceptionalNotes.toTypedArray(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "*** Updated information ***")
            val updatedOrder = testBuilder.admin().orders().update(orderToUpdate)

            assertEquals("Asiakas Matti", updatedOrder.orderInfo!!.customer)
            assertEquals("matti@matti.matti", updatedOrder.orderInfo!!.email)
            assertEquals("Hallituskatu 712", updatedOrder.orderInfo!!.deliveryAddress)
            assertEquals("222222", updatedOrder.orderInfo!!.phoneNumber)
            assertEquals("Otava", updatedOrder.orderInfo!!.city)
            assertEquals(true, updatedOrder.orderInfo!!.socialMediaPermission)
            assertEquals("32", updatedOrder.orderInfo!!.room)
            assertEquals(testDate.split(".")[0], updatedOrder.orderInfo!!.deliveryTime.split(".")[0])
            assertEquals("ABCD", updatedOrder.orderInfo!!.additionalInformation)
            assertEquals("*** Updated information ***", updatedOrder.moreInformation)
            assertEquals("Updated notes", updatedOrder.exceptionalNotes!![0])
        }
    }

    @Test
    fun findOrderTest() {
        TestBuilder().use { testBuilder ->
            val orderId = testBuilder.admin().orders().create(constructTestOrder()).id!!
            assertNotNull(testBuilder.admin().orders().find(orderId))
        }
    }

    @Test
    fun listOrdersTest() {
        TestBuilder().use { testBuilder ->
            val order = constructTestOrder()
            testBuilder.admin().orders().create(order)
            testBuilder.admin().orders().create(order)
            testBuilder.admin().orders().create(order)
            testBuilder.admin().orders().create(order)
            testBuilder.admin().orders().create(order)

            val orders = testBuilder.admin().orders().list()
            assertEquals(5, orders.size)
        }
    }

    private fun constructTestOrder(): Order {
        val orderInfo = OrderInfo(
                "Asiakas Tommi",
                "tommi@tommi.tommi",
                "Hallituskatu 7",
                "1111111",
                "Mikkeli",
                false,
                "3",
                OffsetDateTime.now().toString().replace("+02:00", "Z").replace("+03:00", "Z"),
                "ABC")


        val exceptionalNotes = ArrayList<String>()
        exceptionalNotes.add("-------------")

        return Order(
                null,
                orderInfo,
                null,
                null,
                null,
                exceptionalNotes.toTypedArray(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "*** More information ***")
    }
}


