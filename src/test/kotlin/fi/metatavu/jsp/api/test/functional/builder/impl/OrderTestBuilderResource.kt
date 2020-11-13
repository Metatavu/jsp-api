package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.OrdersApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.infrastructure.ClientException
import fi.metatavu.jsp.api.client.models.*
import fi.metatavu.jsp.api.test.functional.settings.TestSettings
import java.time.OffsetDateTime
import java.util.*

class OrderTestBuilderResource(testBuilder: AbstractTestBuilder<ApiClient?>?, private val accessTokenProvider: AccessTokenProvider?, apiClient: ApiClient): ApiTestBuilderResource<Order, ApiClient>(testBuilder, apiClient) {
    override fun clean(order: Order) {
        return api.deleteOrder(order.id!!)
    }

    /**
     * Sends a request to the API to create an order
     *
     * @param order new order
     *
     * @return a created order
     */
    fun create (order: Order): Order {
        return addClosable(api.createOrder(order))
    }

    /**
     * Sends a request to the API to create an order
     *
     * @return a created order
     */
    fun create (): Order {
        val order = constructTestOrder()
        return addClosable(api.createOrder(order))
    }


    /**
     * Tests that create request fails correctly
     *
     * @param status expected status code
     */
    fun assertCreateFailStatus(status: Int) {
        val order = constructTestOrder()
        order.intermediateSpaces[0] = GenericProduct("a", "a", GenericProductType.dOMESTICAPPLIANCE)
        try {
            api.createOrder(order)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    /**
     * Sends a request to the API to update an order
     *
     * @param order an order to update
     *
     * @return an updated order
     */
    fun update (order: Order): Order {
        return api.updateOrder(order.id!!, order)
    }

    /**
     * Tests that update request fails correctly
     *
     * @param status expected status code
     */
    fun assertUpdateFailStatus(status: Int) {
        val order = constructTestOrder()
        val createdOrder = addClosable(api.createOrder(order))
        createdOrder.intermediateSpaces[0] = GenericProduct("a", "a", GenericProductType.dOMESTICAPPLIANCE)
        try {
            api.updateOrder(createdOrder.id!!, createdOrder)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    /**
     * Sends a request to the API to find an order
     *
     * @param orderId id of an order to find
     *
     * @return found order
     */
    fun find (orderId: UUID): Order {
        return api.findOrder(orderId)
    }

    /**
     * Tests that find request fails correctly
     *
     * @param orderId id of an order to test
     * @param status expected status code
     */
    fun assertFindFailStatus (orderId: UUID, status: Int) {
        try {
            api.findOrder(orderId)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    /**
     * Sends a request to the API to list orders
     *
     * @return an array of orders
     */
    fun list(): Array<Order> {
        return api.listOrders()
    }

    /**
     * Sends a request to the API to delete an order
     *
     * @param orderId the id of an order to delete
     */
    fun delete (orderId: UUID) {
        api.deleteOrder(orderId)
        removeCloseable { closable: Any ->
            if (closable !is Order) {
                return@removeCloseable false
            }

            val closeableTask: Order = closable
            closeableTask.id!! == orderId
        }
    }

    /**
     * Tests that delete request fails correctly
     *
     * @param orderId id of an order to test
     * @param status expected status code
     */
    fun assertDeleteFailStatus (orderId: UUID, status: Int) {
        try {
            api.deleteOrder(orderId)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    override fun getApi(): OrdersApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return OrdersApi(TestSettings.apiBasePath)
    }

    /**
     * Constructs a test order
     */
    fun constructTestOrder(): Order {
        val orderInfo = OrderInfo(
                "Asiakas Tommi",
                "tommi@tommi.tommi",
                "Hallituskatu 7",
                "Hallituskatu 8",
                "Hallituskatu 9",
                false,
                "1111111",
                "Mikkeli",
                false,
                "3",
                OffsetDateTime.now().toString().replace("+02:00", "Z").replace("+03:00", "Z"),
                "ABC")

        val exceptionsFromPlans = ArrayList<String>()
        exceptionsFromPlans.add("-------------")

        val domesticAppliances = ArrayList<GenericProduct>()
        domesticAppliances.add(GenericProduct("Domestic appliance", "INC_1", GenericProductType.dOMESTICAPPLIANCE))

        val otherProducts = ArrayList<GenericProduct>()
        otherProducts.add(GenericProduct("Other product", "INC_2", GenericProductType.oTHER))

        val sinks = ArrayList<GenericProduct>()
        sinks.add(GenericProduct("Sink", "INC_3", GenericProductType.sINK))

        val intermediateSpaces = ArrayList<GenericProduct>()
        intermediateSpaces.add(GenericProduct("Intermediate space", "INC_4", GenericProductType.iNTERMEDIATESPACE))

        val electricProducts = ArrayList<GenericProduct>()
        electricProducts.add(GenericProduct("Electric product", "INC_5", GenericProductType.eLECTRIC))

        val doors = ArrayList<Door>()
        doors.add(Door("Door model", "White", false, "" ))

        val handles = ArrayList<Handle>()
        handles.add(Handle("Door model", "Green", false))

        val counterTops = ArrayList<CounterTop>()
        counterTops.add(CounterTop("Counter top model", "35 mm", CounterTopType.aBS))

        val orderFiles = ArrayList<FileInformation>()

        return Order(
                orderInfo,
                CounterFrame("Red", "Strip", "Plinth", "Extra side", "Information", null),
                doors.toTypedArray(),
                "doors information",
                handles.toTypedArray(),
                "Handles informations",
                counterTops.toTypedArray(),
                "Counter tops informations",
                DrawersInfo("", "" , false, ""),
                domesticAppliances.toTypedArray(),
                "Domestic appliances additional information",
                otherProducts.toTypedArray(),
                "Other products additional information",
                intermediateSpaces.toTypedArray(),
                "Intermediate spaces additional information",
                sinks.toTypedArray(),
                "Sinks additional information",
                electricProducts.toTypedArray(),
                "Electric products additional information",
                Installation(false, null, ""),
                "More information",
                orderFiles.toTypedArray(),
                orderFiles.toTypedArray()
                )
    }
}
