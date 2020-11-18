package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.OrdersApi
import fi.metatavu.jsp.api.spec.model.*
import fi.metatavu.jsp.api.translate.OrderTranslator
import fi.metatavu.jsp.orders.OrdersController
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.products.*
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
    private lateinit var doorsController: DoorsController

    @Inject
    private lateinit var handlesController: HandlesController

    @Inject
    private lateinit var orderTranslator: OrderTranslator

    @Inject
    private lateinit var counterFramesController: CounterFramesController

    @Inject
    private lateinit var counterTopsController: CounterTopsController

    @Inject
    private lateinit var drawersInfoController: DrawersInfoController

    override fun createOrder(order: Order): Response {
        val orderInfo = order.orderInfo

        checkGenericProductsType(order.electricProducts, GenericProductType.ELECTRIC)
                ?: return createBadRequest("Electric products list can only contain products of type ELECTRIC")

        checkGenericProductsType(order.sinks, GenericProductType.SINK)
                ?: return createBadRequest("Sinks list can only contain products of type SINK")

        checkGenericProductsType(order.domesticAppliances, GenericProductType.DOMESTIC_APPLIANCE)
                ?: return createBadRequest("Domestic appliances list can only contain products of type DOMESTIC_APPLIANCE")

        checkGenericProductsType(order.intermediateSpaces, GenericProductType.INTERMEDIATE_SPACE)
                ?: return createBadRequest("Intermediate spaces list can only contain products of type INTERMEDIATE_SPACE")

        checkGenericProductsType(order.otherProducts, GenericProductType.OTHER)
                ?: return createBadRequest("Other products list can only contain products of type OTHER")

        val createdOrder = ordersController.create(
                orderInfo.additionalInformation,
                orderInfo.deliveryTime,
                orderInfo.room,
                orderInfo.socialMediaPermission,
                orderInfo.city,
                orderInfo.phoneNumber,
                orderInfo.deliveryAddress,
                orderInfo.homeAddress,
                orderInfo.billingAddress,
                orderInfo.isHomeBillingAddress,
                orderInfo.email,
                orderInfo.customer,
                order.moreInformation,
                order.sinksAdditionalInformation,
                order.otherProductsAdditionalInformation,
                order.electricProductsAdditionalInformation,
                order.domesticAppliancesAdditionalInformation,
                order.intermediateSpacesAdditionalInformation,
                order.doorsAdditionalInformation,
                order.counterTopsAdditionalInformation,
                order.handlesAdditionalInformation,
                loggerUserId!!
        )

        createGenericProducts(order.sinks, createdOrder)
        createGenericProducts(order.otherProducts, createdOrder)
        createGenericProducts(order.domesticAppliances, createdOrder)
        createGenericProducts(order.electricProducts, createdOrder)
        createGenericProducts(order.intermediateSpaces, createdOrder)

        createHandles(order.handles, createdOrder)
        createDoors(order.doors, createdOrder)
        createCounterTops(order.counterTops, createdOrder)

        val counterFrame = order.counterFrame
        counterFramesController.create(
                createdOrder,
                counterFrame.color,
                counterFrame.cornerStripe,
                counterFrame.extraSide,
                counterFrame.plinth,
                counterFrame.additionalInformation,
                loggerUserId!!
        )

        val drawers = order.drawersInfo
        drawersInfoController.create(
            drawers.trashbins,
            drawers.cutleryCompartments,
            drawers.markedInImages,
            drawers.additionalInformation,
            createdOrder,
            loggerUserId!!
        )



        val translatedOrder = orderTranslator.translate(createdOrder)
        return createOk(translatedOrder)
    }

    /**
     * Saves generic products to a database from a list
     *
     * @param products products to save
     * @param order the order that these products are related to
     */
    private fun createGenericProducts(products: List<GenericProduct>, order: CustomerOrder) {
        for (product in products) {
            if (product.id != null) {
                val existingProduct = genericProductsController.find(product.id)
                if (existingProduct != null) {
                    genericProductsController.update(existingProduct, product.name, product.supplier, loggerUserId!!)
                }
            } else {
                genericProductsController.create(product.name, product.type, product.supplier, order, loggerUserId!!)
            }
        }
    }

    /**
     * Saves doors to a database from a list
     *
     * @param doors doors to save
     * @param order the order that these products are related to
     */
    private fun createDoors(doors: List<Door>, order: CustomerOrder) {
        for (door in doors) {
            if (door.id != null) {
                val existingDoor = doorsController.find(door.id)
                if (existingDoor != null) {
                    doorsController.update(existingDoor, door.isGlassDoor, door.glassColor, door.modelName, door.doorColor, loggerUserId!!)
                }
            } else {
                doorsController.create(order, door.isGlassDoor, door.glassColor, door.modelName, door.doorColor, loggerUserId!!)
            }
        }
    }

    /**
     * Saves generic products to a database from a list
    =======
     * Saves handles to a database from a list
     *
     * @param handles handles to save
     * @param order the order that these handles are related to
     */
    private fun createHandles(handles: List<Handle>, order: CustomerOrder) {
        for (handle in handles) {
            if (handle.id != null) {
                val existingHandle = handlesController.find(handle.id!!)
                if (existingHandle != null) {
                    handlesController.update(existingHandle, handle.doorModelName, handle.color, handle.markedInImages, loggerUserId!!)
                }
            } else {
                handlesController.create(handle.doorModelName, handle.color, handle.markedInImages, order, loggerUserId!!)
            }
        }
    }

    /**
     * Saves counter tops to a database from a list
     *
     * @param counterTops counter tops to save
     * @param order the order that these counter tops are related to
     */
    private fun createCounterTops(counterTops: List<CounterTop>, order: CustomerOrder) {
        for (counterTop in counterTops) {
            if (counterTop.id != null) {
                val existingCounterTop = counterTopsController.find(counterTop.id!!)
                if (existingCounterTop != null) {
                    counterTopsController.update(existingCounterTop, counterTop.type, counterTop.modelName, counterTop.thickness, loggerUserId!!)
                }
            } else {
                counterTopsController.create(order, counterTop.type, counterTop.modelName, counterTop.thickness, loggerUserId!!)
            }
        }
    }
    /**
     * Checks that products match required type
     *
     * @param products products to check
     * @param requiredType required type
     *
     * @return null if failed and true if successful
     */
    private fun checkGenericProductsType(products: List<GenericProduct>, requiredType: GenericProductType): Boolean? {
        for (product in products) {
            if (product.type != requiredType) {
                return null
            }
        }

        return true
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

        val existingGenericProducts = genericProductsController.list(null, existingOrder)

        checkGenericProductsType(order.electricProducts, GenericProductType.ELECTRIC)
                ?: return createBadRequest("Electric products list can only contain products of type ELECTRIC")

        checkGenericProductsType(order.sinks, GenericProductType.SINK)
                ?: return createBadRequest("Sinks list can only contain products of type SINK")

        checkGenericProductsType(order.domesticAppliances, GenericProductType.DOMESTIC_APPLIANCE)
                ?: return createBadRequest("Domestic appliances list can only contain products of type DOMESTIC_APPLIANCE")

        checkGenericProductsType(order.intermediateSpaces, GenericProductType.INTERMEDIATE_SPACE)
                ?: return createBadRequest("Intermediate spaces list can only contain products of type INTERMEDIATE_SPACE")

        checkGenericProductsType(order.otherProducts, GenericProductType.OTHER)
                ?: return createBadRequest("Other products list can only contain products of type OTHER")

        existingGenericProducts.forEach { genericProduct ->
            val products = order.electricProducts
            products.addAll(order.sinks)
            products.addAll(order.intermediateSpaces)
            products.addAll(order.otherProducts)
            products.addAll(order.domesticAppliances)

            val save = products.any { product -> product.id == genericProduct.id }

            if (!save) {
                genericProductsController.delete(genericProduct)
            }
        }

        val existingHandles = handlesController.list(existingOrder)

        existingHandles.forEach { handle ->
            val save = order.handles.any { _handle -> _handle.id == handle.id }

            if (!save) {
                handlesController.delete(handle)
            }
        }

        val existingDoors = doorsController.list(existingOrder)

        existingDoors.forEach { door ->
            val save = order.doors.any { _door -> _door.id == door.id }

            if (!save) {
                doorsController.delete(door)
            }
        }
        val existingCounterTops = counterTopsController.list(existingOrder)

        existingCounterTops.forEach { counterTop ->
            val save = order.counterTops.any { _counterTop -> _counterTop.id == counterTop.id }

            if (!save) {
                counterTopsController.delete(counterTop)
            }
        }

        val orderInfo = order.orderInfo
        val updatedOrder = ordersController.update(
                existingOrder,
                orderInfo.additionalInformation,
                orderInfo.deliveryTime, orderInfo.room,
                orderInfo.socialMediaPermission, orderInfo.city,
                orderInfo.phoneNumber,
                orderInfo.deliveryAddress,
                orderInfo.homeAddress,
                orderInfo.billingAddress,
                orderInfo.isHomeBillingAddress,
                orderInfo.email,
                orderInfo.customer,
                order.moreInformation,
                order.sinksAdditionalInformation,
                order.otherProductsAdditionalInformation,
                order.electricProductsAdditionalInformation,
                order.domesticAppliancesAdditionalInformation,
                order.intermediateSpacesAdditionalInformation,
                order.doorsAdditionalInformation,
                order.counterTopsAdditionalInformation,
                order.handlesAdditionalInformation,
                loggerUserId!!
        )

        createGenericProducts(order.sinks, updatedOrder)
        createGenericProducts(order.otherProducts, updatedOrder)
        createGenericProducts(order.domesticAppliances, updatedOrder)
        createGenericProducts(order.electricProducts, updatedOrder)
        createGenericProducts(order.intermediateSpaces, updatedOrder)

        createHandles(order.handles, updatedOrder)
        createCounterTops(order.counterTops, updatedOrder)
        createDoors(order.doors, updatedOrder)

        val counterFrame = order.counterFrame
        val existingCounterFrame = counterFramesController.find(order.counterFrame.id)!!
        counterFramesController.update(
                existingCounterFrame,
                counterFrame.color,
                counterFrame.cornerStripe,
                counterFrame.extraSide,
                counterFrame.plinth,
                counterFrame.additionalInformation,
                loggerUserId!!
        )

        val drawers = order.drawersInfo
        val existingDrawers = drawersInfoController.find(order.drawersInfo.id)!!
        drawersInfoController.update(
                existingDrawers,
                drawers.trashbins,
                drawers.cutleryCompartments,
                drawers.markedInImages,
                drawers.additionalInformation,
                loggerUserId!!
        )

        return createOk(orderTranslator.translate(updatedOrder))
    }
}
