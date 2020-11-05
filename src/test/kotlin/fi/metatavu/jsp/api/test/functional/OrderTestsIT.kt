package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.client.models.*
import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.OffsetDateTime

/**
 * Tests for orders
 */
class OrderTestsIT: AbstractFunctionalTest() {
    @Test
    fun createOrderTest() {
        TestBuilder().use { testBuilder ->
            val testOrder = testBuilder.admin().orders().constructTestOrder()
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
            assertEquals(testOrder.exceptionsFromPlans!![0], createdOrder.exceptionsFromPlans!![0])

            assertNotNull(createdOrder.sinks?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.sinks!![0], createdOrder.sinks!![0])

            assertNotNull(createdOrder.electricProducts?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.electricProducts!![0], createdOrder.electricProducts!![0])

            assertNotNull(createdOrder.domesticAppliances?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.domesticAppliances!![0], createdOrder.domesticAppliances!![0])

            assertNotNull(createdOrder.otherProducts?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.otherProducts!![0], createdOrder.otherProducts!![0])

            assertNotNull(createdOrder.intermediateSpaces?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.intermediateSpaces!![0], createdOrder.intermediateSpaces!![0])
        }
    }

    @Test
    fun updateOrderTest() {
        TestBuilder().use { testBuilder ->
            val testOrder = testBuilder.admin().orders().constructTestOrder()
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

            val exceptionsFromPlans = ArrayList<String>()
            exceptionsFromPlans.add("Updated notes")

            val domesticAppliances = ArrayList<GenericProduct>()
            domesticAppliances.add(GenericProduct("Domestic appliance 2", "INC_1_U", GenericProductType.dOMESTICAPPLIANCE))

            val otherProducts = ArrayList<GenericProduct>()
            otherProducts.add(GenericProduct("Other product 2", "INC_2_U", GenericProductType.oTHER))

            val sinks = ArrayList<GenericProduct>()
            sinks.add(GenericProduct("Sink 2", "INC_3_U", GenericProductType.sINK))

            val intermediateSpaces = ArrayList<GenericProduct>()
            intermediateSpaces.add(GenericProduct("Intermediate space 2", "INC_4_U", GenericProductType.iNTERMEDIATESPACE))

            val electricProducts = ArrayList<GenericProduct>()
            electricProducts.add(GenericProduct("Electric product 2", "INC_5_U", GenericProductType.eLECTRIC))

            val orderToUpdate = Order(createdOrder.id,
                    orderInfo2,
                    null,
                    null,
                    null,
                    exceptionsFromPlans.toTypedArray(),
                    null,
                    domesticAppliances.toTypedArray(),
                    otherProducts.toTypedArray(),
                    intermediateSpaces.toTypedArray(),
                    sinks.toTypedArray(),
                    electricProducts.toTypedArray(),
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
            assertEquals("Updated notes", updatedOrder.exceptionsFromPlans!![0])

            assertNotNull(updatedOrder.sinks?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.sinks!![0], updatedOrder.sinks!![0])

            assertNotNull(updatedOrder.electricProducts?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.electricProducts!![0], updatedOrder.electricProducts!![0])

            assertNotNull(updatedOrder.domesticAppliances?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.domesticAppliances!![0], updatedOrder.domesticAppliances!![0])

            assertNotNull(updatedOrder.otherProducts?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.otherProducts!![0], updatedOrder.otherProducts!![0])

            assertNotNull(updatedOrder.intermediateSpaces?.get(0))
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.intermediateSpaces!![0], updatedOrder.intermediateSpaces!![0])
        }
    }

    @Test
    fun findOrderTest() {
        TestBuilder().use { testBuilder ->
            val orderId = testBuilder.admin().orders().create().id!!
            assertNotNull(testBuilder.admin().orders().find(orderId))
        }
    }

    @Test
    fun listOrdersTest() {
        TestBuilder().use { testBuilder ->
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()
            testBuilder.admin().orders().create()

            val orders = testBuilder.admin().orders().list()
            assertEquals(5, orders.size)
        }
    }
}


