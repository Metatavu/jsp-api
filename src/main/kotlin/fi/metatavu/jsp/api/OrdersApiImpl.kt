package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.OrdersApi
import fi.metatavu.jsp.api.spec.model.GenericProduct
import fi.metatavu.jsp.api.spec.model.GenericProductType
import fi.metatavu.jsp.api.spec.model.Order
import fi.metatavu.jsp.api.translate.OrderTranslator
import fi.metatavu.jsp.orders.OrdersController
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.products.GenericProductsController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * Endpoints for orders
 */
@Stateful
@RequestScoped
class OrdersApiImpl: OrdersApi, AbstractApi() {
    @Inject
    private lateinit var ordersController: OrdersController

    @Inject
    private lateinit var genericProductsController: GenericProductsController

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

        createGenericProducts(order.sinks, createdOrder, GenericProductType.SINK)
                ?: return createBadRequest("Sinks list can only contain products of type SINK")

        createGenericProducts(order.otherProducts, createdOrder, GenericProductType.OTHER)
                ?: return createBadRequest("Other products list can only contain products of type OTHER")

        createGenericProducts(order.domesticAppliances, createdOrder, GenericProductType.DOMESTIC_APPLIANCE)
                ?: return createBadRequest("Domestic appliances list can only contain products of type DOMESTIC_APPLIANCE")

        createGenericProducts(order.electricProducts, createdOrder, GenericProductType.ELECTRIC)
                ?: return createBadRequest("Electric products list can only contain products of type ELECTRIC")

        createGenericProducts(order.intermediateSpaces, createdOrder, GenericProductType.INTERMEDIATE_SPACE)
                ?: return createBadRequest("Intermediate spaces list can only contain products of type INTERMEDIATE_SPACE")

        val translatedOrder = orderTranslator.translate(createdOrder)
        return createOk(translatedOrder)
    }

    /**
     * Checks that products match a required type and saves generic products to a database from a list
     *
     * @param products products to save
     * @param order the order that these products are related to
     * @param requiredType required type
     *
     * @return empty object if all type checks are successful and null if any of them fails
     */
    private fun createGenericProducts (products: List<GenericProduct>, order: CustomerOrder, requiredType: GenericProductType): Any? {
        for (product in products) {
            val type = product.type
            if (type != requiredType) {
                return null
            }

            genericProductsController.create(product.name, type, product.supplier, order, loggerUserId!!)
        }

        return {}
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

        val genericProducts = genericProductsController.list(null, existingOrder)

        genericProducts.forEach { genericProduct ->
            genericProductsController.delete(genericProduct)
        }

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

        createGenericProducts(order.sinks, updatedOrder, GenericProductType.SINK)
                ?: return createBadRequest("Sinks list can only contain products of type SINK")

        createGenericProducts(order.otherProducts, updatedOrder, GenericProductType.OTHER)
                ?: return createBadRequest("Other products list can only contain products of type OTHER")

        createGenericProducts(order.domesticAppliances, updatedOrder, GenericProductType.DOMESTIC_APPLIANCE)
                ?: return createBadRequest("Domestic appliances list can only contain products of type DOMESTIC_APPLIANCE")

        createGenericProducts(order.electricProducts, updatedOrder, GenericProductType.ELECTRIC)
                ?: return createBadRequest("Electric products list can only contain products of type ELECTRIC")

        createGenericProducts(order.intermediateSpaces, updatedOrder, GenericProductType.INTERMEDIATE_SPACE)
                ?: return createBadRequest("Intermediate spaces list can only contain products of type INTERMEDIATE_SPACE")

        val translatedOrder = orderTranslator.translate(updatedOrder)
        return createOk(translatedOrder)
    }
}