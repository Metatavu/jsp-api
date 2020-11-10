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

            assertEquals(testOrder.orderInfo.customer, createdOrder.orderInfo.customer)
            assertEquals(testOrder.orderInfo.email, createdOrder.orderInfo.email)
            assertEquals(testOrder.orderInfo.deliveryAddress, createdOrder.orderInfo.deliveryAddress)
            assertEquals(testOrder.orderInfo.phoneNumber, createdOrder.orderInfo.phoneNumber)
            assertEquals(testOrder.orderInfo.city, createdOrder.orderInfo.city)
            assertEquals(testOrder.orderInfo.socialMediaPermission, createdOrder.orderInfo.socialMediaPermission)
            assertEquals(testOrder.orderInfo.room, createdOrder.orderInfo.room)
            assertEquals(testOrder.orderInfo.deliveryTime.split(".")[0], createdOrder.orderInfo.deliveryTime.split(".")[0])
            assertEquals(testOrder.orderInfo.additionalInformation, createdOrder.orderInfo.additionalInformation)
            assertEquals(testOrder.moreInformation, createdOrder.moreInformation)
            assertEquals(testOrder.orderInfo.homeAddress, createdOrder.orderInfo.homeAddress)
            assertEquals(testOrder.orderInfo.billingAddress, createdOrder.orderInfo.billingAddress)
            assertEquals(testOrder.orderInfo.isHomeBillingAddress, createdOrder.orderInfo.isHomeBillingAddress)

            assertEquals(testOrder.sinksAdditionalInformation, createdOrder.sinksAdditionalInformation)
            assertEquals(testOrder.intermediateSpacesAdditionalInformation, createdOrder.intermediateSpacesAdditionalInformation)
            assertEquals(testOrder.otherProductsAdditionalInformation, createdOrder.otherProductsAdditionalInformation)
            assertEquals(testOrder.electricProductsAdditionalInformation, createdOrder.electricProductsAdditionalInformation)
            assertEquals(testOrder.domesticAppliancesAdditionalInformation, createdOrder.domesticAppliancesAdditionalInformation)
            assertEquals(testOrder.handlesAdditionalInformation, createdOrder.handlesAdditionalInformation)
            assertEquals(testOrder.counterTopsAdditionalInformation, createdOrder.counterTopsAdditionalInformation)


            assertNotNull(createdOrder.sinks[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.sinks[0], createdOrder.sinks[0])

            assertNotNull(createdOrder.electricProducts[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.electricProducts[0], createdOrder.electricProducts[0])

            assertNotNull(createdOrder.domesticAppliances[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.domesticAppliances[0], createdOrder.domesticAppliances[0])

            assertNotNull(createdOrder.otherProducts[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.otherProducts[0], createdOrder.otherProducts[0])

            assertNotNull(createdOrder.intermediateSpaces[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.intermediateSpaces[0], createdOrder.intermediateSpaces[0])
                           
            assertNotNull(createdOrder.handles[0])
            testBuilder.admin().handles().assertHandlesEqual(testOrder.handles[0], createdOrder.handles[0])

            assertNotNull(createdOrder.counterTops[0])
            testBuilder.admin().counterTops().assertCounterTopsEqual(createdOrder.counterTops[0], createdOrder.counterTops[0])

            testBuilder.admin().counterFrames().assertCounterFramesEqual(testOrder.counterFrame, createdOrder.counterFrame)

            testBuilder.admin().orders().assertCreateFailStatus(400)
        }
    }

    @Test
    fun updateOrderTest() {
        TestBuilder().use { testBuilder ->
            val createdOrder = testBuilder.admin().orders().create()

            val testDate = OffsetDateTime.now().plusDays(23).toString().replace("+02:00", "Z").replace("+03:00", "Z")
            val orderInfo2 = OrderInfo(
                    "Asiakas Matti",
                    "matti@matti.matti",
                    "Hallituskatu 712",
                    "Hallituskatu 713",
                    "Hallituskatu 714",
                    true,
                    "222222",
                    "Otava",
                    true,
                    "32",
                    testDate,
                    "ABCD")

            val exceptionsFromPlans = ArrayList<String>()
            exceptionsFromPlans.add("Updated notes")

            val domesticAppliances = ArrayList<GenericProduct>()
            domesticAppliances.addAll(createdOrder.domesticAppliances)
            domesticAppliances[0] = GenericProduct("Domestic appliance 2", "INC_1_U", GenericProductType.dOMESTICAPPLIANCE)

            val otherProducts = ArrayList<GenericProduct>()
            otherProducts.add(GenericProduct("Other product 2", "INC_2_U", GenericProductType.oTHER))

            val sinks = ArrayList<GenericProduct>()
            sinks.add(GenericProduct("Sink 2", "INC_3_U", GenericProductType.sINK))

            val intermediateSpaces = ArrayList<GenericProduct>()
            intermediateSpaces.add(GenericProduct("Intermediate space 2", "INC_4_U", GenericProductType.iNTERMEDIATESPACE))

            val electricProducts = ArrayList<GenericProduct>()
            electricProducts.add(GenericProduct("Electric product 2", "INC_5_U", GenericProductType.eLECTRIC))

            val doors = ArrayList<Door>()

            val handles = ArrayList<Handle>()
            handles.add(Handle("Door model 2", "Yellow", true))

            val counterTops = ArrayList<CounterTop>()
            counterTops.add(CounterTop("Update counter top model", "55 mm", CounterTopType.sT))

            val orderFiles = ArrayList<FileInformation>()

            val orderToUpdate = Order(
                    orderInfo2,
                    CounterFrame("Red", "Strip", "Plinth", "Extra side", "Information", createdOrder.counterFrame.id),
                    doors.toTypedArray(),
                    "",
                    handles.toTypedArray(),
                    "Updated handles information",
                    counterTops.toTypedArray(),
                    "Updated counter tops information",
                    DrawersInfo("", "" , false, ""),
                    domesticAppliances.toTypedArray(),
                    "Domestic appliances additional information 2",
                    otherProducts.toTypedArray(),
                    "Other products additional information 2",
                    intermediateSpaces.toTypedArray(),
                    "Intermediate spaces additional information 2",
                    sinks.toTypedArray(),
                    "Sinks additional information 2",
                    electricProducts.toTypedArray(),
                    "Electric products additional information 2",
                    Installation(false, null, ""),
                    "*** Updated information ***",
                    orderFiles.toTypedArray(),
                    orderFiles.toTypedArray(),
                    createdOrder.id)
            val updatedOrder = testBuilder.admin().orders().update(orderToUpdate)

            assertEquals("Asiakas Matti", updatedOrder.orderInfo.customer)
            assertEquals("matti@matti.matti", updatedOrder.orderInfo.email)
            assertEquals("Hallituskatu 712", updatedOrder.orderInfo.deliveryAddress)
            assertEquals("Hallituskatu 713", updatedOrder.orderInfo.homeAddress)
            assertEquals("Hallituskatu 714", updatedOrder.orderInfo.billingAddress)
            assertEquals(true, updatedOrder.orderInfo.isHomeBillingAddress)
            assertEquals("222222", updatedOrder.orderInfo.phoneNumber)
            assertEquals("Otava", updatedOrder.orderInfo.city)
            assertEquals(true, updatedOrder.orderInfo.socialMediaPermission)
            assertEquals("32", updatedOrder.orderInfo.room)
            assertEquals(testDate.split(".")[0], updatedOrder.orderInfo.deliveryTime.split(".")[0])
            assertEquals("ABCD", updatedOrder.orderInfo.additionalInformation)
            assertEquals("*** Updated information ***", updatedOrder.moreInformation)

            assertEquals("Domestic appliances additional information 2", updatedOrder.domesticAppliancesAdditionalInformation)
            assertEquals("Other products additional information 2", updatedOrder.otherProductsAdditionalInformation)
            assertEquals("Intermediate spaces additional information 2", updatedOrder.intermediateSpacesAdditionalInformation)
            assertEquals("Sinks additional information 2", updatedOrder.sinksAdditionalInformation)
            assertEquals("Electric products additional information 2", updatedOrder.electricProductsAdditionalInformation)
            assertEquals("Updated counter tops information", updatedOrder.counterTopsAdditionalInformation)
            assertEquals("Updated handles information", updatedOrder.handlesAdditionalInformation)

            assertNotNull(updatedOrder.sinks[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.sinks[0], updatedOrder.sinks[0])

            assertNotNull(updatedOrder.electricProducts[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.electricProducts[0], updatedOrder.electricProducts[0])

            assertNotNull(updatedOrder.domesticAppliances[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.domesticAppliances[0], updatedOrder.domesticAppliances[0])

            assertNotNull(updatedOrder.otherProducts[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.otherProducts[0], updatedOrder.otherProducts[0])

            assertNotNull(updatedOrder.intermediateSpaces[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.intermediateSpaces[0], updatedOrder.intermediateSpaces[0])

            assertNotNull(updatedOrder.handles[0])
            testBuilder.admin().handles().assertHandlesEqual(orderToUpdate.handles[0], updatedOrder.handles[0])

            assertNotNull(updatedOrder.counterTops[0])
            testBuilder.admin().counterTops().assertCounterTopsEqual(orderToUpdate.counterTops[0], updatedOrder.counterTops[0])

            testBuilder.admin().counterFrames().assertCounterFramesEqual(orderToUpdate.counterFrame, updatedOrder.counterFrame)


            testBuilder.admin().orders().assertUpdateFailStatus(400)
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

    @Test
    fun deleteOrderTest() {

        TestBuilder().use { testBuilder ->
            val orderId = testBuilder.admin().orders().create().id!!
            testBuilder.admin().orders().delete(orderId)
            testBuilder.admin().orders().assertFindFailStatus(orderId, 404)
            testBuilder.admin().orders().assertDeleteFailStatus(orderId, 404)
        }

    }
}


