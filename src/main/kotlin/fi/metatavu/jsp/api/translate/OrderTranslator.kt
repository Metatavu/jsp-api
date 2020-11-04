package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.*
import fi.metatavu.jsp.orders.OrdersController
import fi.metatavu.jsp.persistence.model.CustomerOrder
import javax.inject.Inject

/**
 * A translator class for orders
 */
class OrderTranslator: AbstractTranslator<CustomerOrder, Order>() {
    @Inject
    private lateinit var ordersController: OrdersController

    /**
     * Translates JPA orders into REST orders
     *
     * @param entity JPA order to translate
     *
     * @return translated order
     */
    override fun translate(entity: CustomerOrder): Order {
        val order = Order()
        order.id = entity.id

        val orderInfo = OrderInfo()
        orderInfo.additionalInformation = entity.additionalInformation
        orderInfo.city = entity.city
        orderInfo.customer = entity.customer
        orderInfo.deliveryAddress = entity.deliveryAddress
        orderInfo.deliveryTime = entity.deliveryTime
        orderInfo.email = entity.email
        orderInfo.additionalInformation = entity.additionalInformation
        orderInfo.phoneNumber = entity.phoneNumber
        orderInfo.room = entity.room
        orderInfo.socialMediaPermission = entity.socialMediaPermission

        val doorsInfo = DoorsInfo()
        doorsInfo.isGlassDoor = false
        doorsInfo.doorModels = ArrayList()
        doorsInfo.glassColor = ""

        val drawersInfo = DrawersInfo()
        drawersInfo.additionalInformation = ""
        drawersInfo.trashbins = ""
        drawersInfo.cutleryCompartments = ""
        drawersInfo.markedInImages = false

        val installation = Installation()
        installation.isCustomerInstallation = false
        installation.additionalInformation = ""

        order.orderInfo = orderInfo
        order.doors = doorsInfo
        order.drawersInfo = drawersInfo
        order.installation = installation
        order.handles = ArrayList()
        order.counterTops = ArrayList()
        order.exceptionsFromPlans = ordersController.listExceptionsFromPlans(entity).map { note -> note.note }
        order.domesticAppliances = ArrayList()
        order.otherProducts = ArrayList()
        order.intermediateSpaces = ArrayList()
        order.sinks = ArrayList()
        order.electricProducts = ArrayList()
        order.moreInformation = entity.moreInformation
        order.customerFiles = ArrayList()
        order.orderFiles = ArrayList()
        order.createdAt = entity.createdAt
        order.modifiedAt = entity.modifiedAt
        order.creatorId = entity.creatorId
        order.modifierId = entity.lastModifierId

        return order
    }
}