package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.OrdersApi
import fi.metatavu.jsp.api.spec.model.Order
import fi.metatavu.jsp.api.translate.OrderTranslator
import fi.metatavu.jsp.orders.OrdersController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * REST endpoints for orders
 */
@Stateful
@RequestScoped
class OrdersApiImpl: OrdersApi, AbstractApi() {
    @Inject
    private lateinit var ordersController: OrdersController

    @Inject
    private lateinit var orderTranslator: OrderTranslator

    override fun createOrder(order: Order): Response {
        val orderInfo = order.orderInfo

        val createdOrder = ordersController.create(
                orderInfo.additionalInformation,
                orderInfo.deliveryTime,
                orderInfo.room,
                orderInfo.socialMediaPermission,
                orderInfo.city,
                orderInfo.phoneNumber,
                orderInfo.deliveryAddress,
                orderInfo.email,
                orderInfo.customer,
                order.moreInformation,
                order.exceptionsFromPlans,
                loggerUserId!!
        )
        val translatedOrder = orderTranslator.translate(createdOrder)
        return createOk(translatedOrder)
    }

    override fun deleteOrder(orderId: UUID): Response {
        val order = ordersController.find(orderId) ?: return createNotFound("Order with id $orderId not found!")
        ordersController.delete(order)
        return createNoContent()
    }

    override fun findOrder(orderId: UUID): Response {
        val order = ordersController.find(orderId) ?: return createNotFound("Order with id $orderId not found!")
        val translatedOrder = orderTranslator.translate(order)
        return createOk(translatedOrder)
    }

    override fun listOrders(): Response {
        val orders = ordersController.list()
        val translatedOrders = orders.map(orderTranslator::translate)
        return createOk(translatedOrders)
    }

    override fun updateOrder(orderId: UUID, order: Order): Response {
        val existingOrder = ordersController.find(orderId) ?: return createNotFound("Order with id $orderId not found!")
        val orderInfo = order.orderInfo
        val updatedOrder = ordersController.update(
                existingOrder,
                orderInfo.additionalInformation,
                orderInfo.deliveryTime, orderInfo.room,
                orderInfo.socialMediaPermission, orderInfo.city,
                orderInfo.phoneNumber,
                orderInfo.deliveryAddress,
                orderInfo.email,
                orderInfo.customer,
                order.moreInformation,
                order.exceptionsFromPlans,
                loggerUserId!!
        )
        val translatedOrder = orderTranslator.translate(updatedOrder)
        return createOk(translatedOrder)
    }
}