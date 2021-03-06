package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.client.models.*
import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.joda.time.tz.ZoneInfoProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
            assertEquals(testOrder.orderInfo.price, createdOrder.orderInfo.price)
            assertEquals(testOrder.orderInfo.priceTaxFree, createdOrder.orderInfo.priceTaxFree)
            assertEquals(testOrder.sentToCustomerAt, createdOrder.sentToCustomerAt)
            assertEquals(testOrder.seenByManagerAt, createdOrder.seenByManagerAt)
            assertEquals(testOrder.orderStatus, createdOrder.orderStatus)
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
            assertEquals(testOrder.mechanismsAdditionalInformation, createdOrder.mechanismsAdditionalInformation)

            assertEquals(testOrder.handlesAdditionalInformation, createdOrder.handlesAdditionalInformation)
            assertEquals(testOrder.counterTopsAdditionalInformation, createdOrder.counterTopsAdditionalInformation)
            assertEquals(testOrder.doorsAdditionalInformation, createdOrder.doorsAdditionalInformation)

            assertEquals(testOrder.customerFiles[0].name, createdOrder.customerFiles[0].name)
            assertEquals(testOrder.orderFiles[0].name, createdOrder.orderFiles[0].name)

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

            assertNotNull(createdOrder.mechanisms[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(testOrder.mechanisms[0], createdOrder.mechanisms[0])

            assertNotNull(createdOrder.handles[0])
            testBuilder.admin().handles().assertHandlesEqual(testOrder.handles[0], createdOrder.handles[0])

            assertNotNull(createdOrder.counterTops[0])
            testBuilder.admin().counterTops().assertCounterTopsEqual(createdOrder.counterTops[0], createdOrder.counterTops[0])

            assertNotNull(createdOrder.doors[0])
            testBuilder.admin().doors().assertDoorsEqual(testOrder.doors[0], createdOrder.doors[0])

            assertNotNull(createdOrder.drawersInfo)
            testBuilder.admin().drawers().assertDrawersEqual(testOrder.drawersInfo, createdOrder.drawersInfo)

            assertNotNull(createdOrder.installation)
            testBuilder.admin().installations().assertInstallationsEqual(testOrder.installation, createdOrder.installation)

            testBuilder.admin().counterFrames().assertCounterFramesEqual(testOrder.counterFrame, createdOrder.counterFrame)
            testBuilder.admin().doors().assertDoorsEqual(testOrder.doors[0], createdOrder.doors[0])

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
                    "ABCD",
                    23.00,
                    21.00)

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

            val mechanisms = ArrayList<GenericProduct>()
            mechanisms.add(GenericProduct("Mechanisms 2", "INC_6_U", GenericProductType.mECHANISM))

            val doors = ArrayList<Door>()
            doors.add(Door("model name", "White", true, "green"))

            val handles = ArrayList<Handle>()
            handles.add(Handle("Door model 2", "Yellow", true))

            val counterTops = ArrayList<CounterTop>()
            counterTops.add(CounterTop("Update counter top model", "55 mm", CounterTopType.sT))

            val orderFiles = createdOrder.orderFiles
            val customerFiles = ArrayList<FileInformation>()

            val orderToUpdate = Order(
                    OrderStatus.oRDER,
                    orderInfo2,
                    CounterFrame("Red2", "Strip2", "Plinth2", "Extra side2", "Information", createdOrder.counterFrame.id),
                    doors.toTypedArray(),
                    "Updated doors information",
                    handles.toTypedArray(),
                    "Updated handles information",
                    counterTops.toTypedArray(),
                    "Updated counter tops information",
                    DrawersInfo("updated trash bins", "updated cutlery", true, "updated info", createdOrder.drawersInfo.id),
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
                    mechanisms.toTypedArray(),
                    "Mechanisms additional information 2",
                    Installation(false, createdOrder.installation.id, "updated additional information"),
                    "*** Updated information ***",
                    customerFiles = customerFiles.toTypedArray(),
                    orderFiles = orderFiles,
                    id = createdOrder.id,
                    seenByManagerAt = OffsetDateTime.now().toString(),
                    sentToCustomerAt = OffsetDateTime.now().toString())
            val updatedOrder = testBuilder.admin().orders().update(orderToUpdate)

            assertEquals("Asiakas Matti", updatedOrder.orderInfo.customer)
            assertEquals(OrderStatus.oRDER, updatedOrder.orderStatus)
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
            assertEquals(orderToUpdate.orderInfo.price, updatedOrder.orderInfo.price)
            assertEquals(orderToUpdate.orderInfo.priceTaxFree, updatedOrder.orderInfo.priceTaxFree)
            assertEquals("Domestic appliances additional information 2", updatedOrder.domesticAppliancesAdditionalInformation)
            assertEquals("Other products additional information 2", updatedOrder.otherProductsAdditionalInformation)
            assertEquals("Intermediate spaces additional information 2", updatedOrder.intermediateSpacesAdditionalInformation)
            assertEquals("Sinks additional information 2", updatedOrder.sinksAdditionalInformation)
            assertEquals("Electric products additional information 2", updatedOrder.electricProductsAdditionalInformation)
            assertEquals("Mechanisms additional information 2", updatedOrder.mechanismsAdditionalInformation)
            assertEquals("Updated counter tops information", updatedOrder.counterTopsAdditionalInformation)
            assertEquals("Updated handles information", updatedOrder.handlesAdditionalInformation)
            assertEquals("Updated doors information", updatedOrder.doorsAdditionalInformation)

            assertEquals(OffsetDateTime.parse(orderToUpdate.seenByManagerAt).compareTo(OffsetDateTime.parse(orderToUpdate.seenByManagerAt)), 0)
            assertEquals(OffsetDateTime.parse(orderToUpdate.sentToCustomerAt).compareTo(OffsetDateTime.parse(orderToUpdate.sentToCustomerAt)), 0)

            assertEquals(updatedOrder.orderFiles.count(), orderToUpdate.orderFiles.count())
            assertEquals(updatedOrder.customerFiles.count(), orderToUpdate.customerFiles.count())

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

            assertNotNull(updatedOrder.mechanisms[0])
            testBuilder.admin().genericProducts().assertGenericProductsEqual(orderToUpdate.mechanisms[0], updatedOrder.mechanisms[0])

            assertNotNull(updatedOrder.handles[0])
            testBuilder.admin().handles().assertHandlesEqual(orderToUpdate.handles[0], updatedOrder.handles[0])

            assertNotNull(updatedOrder.counterTops[0])
            testBuilder.admin().counterTops().assertCounterTopsEqual(orderToUpdate.counterTops[0], updatedOrder.counterTops[0])

            assertNotNull(updatedOrder.doors)
            testBuilder.admin().doors().assertDoorsEqual(orderToUpdate.doors[0], updatedOrder.doors[0])

            assertNotNull(updatedOrder.drawersInfo)
            testBuilder.admin().drawers().assertDrawersEqual(orderToUpdate.drawersInfo, updatedOrder.drawersInfo)

            assertNotNull(updatedOrder.installation)
            testBuilder.admin().installations().assertInstallationsEqual(orderToUpdate.installation, updatedOrder.installation)

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
