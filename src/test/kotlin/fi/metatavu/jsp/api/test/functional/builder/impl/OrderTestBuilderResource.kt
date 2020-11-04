package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.OrdersApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.models.*
import fi.metatavu.jsp.api.test.functional.settings.TestSettings
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
     * Sends a request to the API to list orders
     *
     * @return an array of orders
     */
    fun list(): Array<Order> {
        return api.listOrders()
    }

    override fun getApi(): OrdersApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return OrdersApi(TestSettings.apiBasePath)
    }
}