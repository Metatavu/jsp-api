package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.*
import fi.metatavu.jsp.orders.OrdersController
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.products.CounterFramesController
import fi.metatavu.jsp.products.GenericProductsController
import fi.metatavu.jsp.products.HandlesController
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * A translator class for orders
 */
@ApplicationScoped
class OrderTranslator: AbstractTranslator<CustomerOrder, Order>() {
    @Inject
    private lateinit var ordersController: OrdersController

    @Inject
    private lateinit var genericProductTranslator: GenericProductTranslator

    @Inject
    private lateinit var genericProductsController: GenericProductsController

    @Inject

    private lateinit var handleTranslator: HandleTranslator

    @Inject
    private lateinit var handlesController: HandlesController

    private lateinit var counterFramesController: CounterFramesController

    @Inject
    private lateinit var counterFrameTranslator: CounterFrameTranslator

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
        orderInfo.homeAddress = entity.homeAddress
        orderInfo.billingAddress = entity.billingAddress
        orderInfo.isHomeBillingAddress = entity.isHomeBillingAddress

        orderInfo.deliveryTime = entity.deliveryTime
        orderInfo.email = entity.email
        orderInfo.additionalInformation = entity.additionalInformation
        orderInfo.phoneNumber = entity.phoneNumber
        orderInfo.room = entity.room
        orderInfo.socialMediaPermission = entity.socialMediaPermission

        val drawersInfo = DrawersInfo()
        drawersInfo.additionalInformation = ""
        drawersInfo.trashbins = ""
        drawersInfo.cutleryCompartments = ""
        drawersInfo.markedInImages = false

        val installation = Installation()
        installation.isCustomerInstallation = false
        installation.additionalInformation = ""

        val counterFrame = CounterFrame()
        counterFrame.color = ""
        counterFrame.cornerStripe = ""
        counterFrame.extraSide = ""
        counterFrame.plinth = ""
        counterFrame.additionalInformation = ""

        order.orderInfo = orderInfo
        order.doors = ArrayList()
        order.drawersInfo = drawersInfo
        order.installation = installation
        order.handles = handlesController.list(entity).map(handleTranslator::translate)
        order.counterTops = ArrayList()
        order.counterFrame = counterFramesController.list(entity).map(counterFrameTranslator::translate)[0]

        order.domesticAppliances = genericProductsController.list(GenericProductType.DOMESTIC_APPLIANCE, entity).map(genericProductTranslator::translate)
        order.otherProducts = genericProductsController.list(GenericProductType.OTHER, entity).map(genericProductTranslator::translate)
        order.intermediateSpaces = genericProductsController.list(GenericProductType.INTERMEDIATE_SPACE, entity).map(genericProductTranslator::translate)
        order.sinks = genericProductsController.list(GenericProductType.SINK, entity).map(genericProductTranslator::translate)
        order.electricProducts = genericProductsController.list(GenericProductType.ELECTRIC, entity).map(genericProductTranslator::translate)

        order.domesticAppliancesAdditionalInformation = entity.domesticAppliancesInformation
        order.intermediateSpacesAdditionalInformation = entity.intermediateSpacesInformation
        order.sinksAdditionalInformation = entity.sinksInformation
        order.otherProductsAdditionalInformation = entity.otherProductsInformation
        order.electricProductsAdditionalInformation = entity.electricProductsInformation

        order.doorsAdditionalInformation = ""
        order.handlesAdditionalInformation = ""
        order.counterTopsAdditionalInformation = ""

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