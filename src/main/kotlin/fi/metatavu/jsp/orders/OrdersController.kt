package fi.metatavu.jsp.orders

import fi.metatavu.jsp.api.spec.model.OrderStatus
import fi.metatavu.jsp.persistence.dao.OrderDAO
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.products.*
import java.time.OffsetDateTime
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * A controller for orders
 */
@ApplicationScoped
class OrdersController {
    @Inject
    private lateinit var orderDAO: OrderDAO

    @Inject
    private lateinit var genericProductsController: GenericProductsController

    @Inject
    private lateinit var handlesController: HandlesController

    @Inject
    private lateinit var counterFramesController: CounterFramesController

    @Inject
    private lateinit var counterTopsController: CounterTopsController

    @Inject
    private lateinit var doorsController: DoorsController

    @Inject
    private lateinit var drawersInfoController: DrawersInfoController

    @Inject
    private lateinit var installationsController: InstallationsController

    @Inject
    private lateinit var filesController: FilesController
    /**
     * Lists all orders
     *
     * @return a list of orders
     */
    fun list(): List<CustomerOrder> {
        return orderDAO.listAll()
    }

    /**
     * Finds all orders
     *
     * @param orderId id of the order to find
     *
     * @return found order or null if not found
     */
    fun find (orderId: UUID): CustomerOrder? {
        return orderDAO.findById(orderId)
    }

    /**
     * Deletes an order
     *
     * @param customerOrder order to be deleted
     */
    fun delete (customerOrder: CustomerOrder) {
        val products = genericProductsController.list(null, customerOrder)

        products.forEach { product ->
            genericProductsController.delete(product)
        }

        val handles = handlesController.list(customerOrder)

        handles.forEach { handle ->
            handlesController.delete(handle)

        }
        val counterTops = counterTopsController.list(customerOrder)

        counterTops.forEach { counterTop ->
            counterTopsController.delete(counterTop)
        }

        val counterFrames = counterFramesController.list(customerOrder)

        counterFrames.forEach { counterFrame ->
            counterFramesController.delete(counterFrame)
        }

        val doors = doorsController.list(customerOrder)

        doors.forEach { door ->
            doorsController.delete(door)
        }

        val drawersInfo = drawersInfoController.list(customerOrder)

        drawersInfo.forEach { drawers ->
            drawersInfoController.delete(drawers)
        }

        val installations = installationsController.list(customerOrder)

        installations.forEach { installation ->
            installationsController.delete(installation)
        }

        val customerFiles = filesController.list(customerOrder, true)
        customerFiles.forEach { filesController.delete(it) }

        val orderFiles = filesController.list(customerOrder, false)
        orderFiles.forEach { filesController.delete(it) }

        installations.forEach { installation ->
            installationsController.delete(installation)
        }

        orderDAO.delete(customerOrder)
    }

    /**
     * Saves a new order to the database
     *
     * @param orderStatus orderStatus
     * @param seenByManagerAt seen by manager at timestamp
     * @param sentToCustomerAt sent to customer at timestamp
     * @param additionalInformation additional information
     * @param deliveryTime time of the delivery
     * @param room room
     * @param socialMediaPermission social media permission
     * @param city city
     * @param phoneNumber customer phone number
     * @param deliveryAddress delivery address
     *
     * @param homeAddress home address
     * @param billingAddress billing address
     * @param isHomeBillingAddress is home address billing address
     *
     * @param emailAddress customer email address
     * @param customer customer name
     * @param moreInformation more information
     *
     * @param sinksInformation sinks additional information
     * @param otherProductsInformation other products additional information
     * @param electricProductsInformation electric products additional information
     * @param domesticAppliancesInformation domestic appliances additional information
     * @param intermediateSpacesInformation intermediate spaces additional information
     * @param mechanismsInformation mechanisms additional information
     *
     * @param counterTopsInformation counter tops information
     * @param handlesInformation handles information
     * @param doorsInformation
     *
     * @param creatorId id of the user who creates this order
     *
     * @return a new order
     */
    fun create (orderStatus: OrderStatus,
                seenByManagerAt: OffsetDateTime?,
                sentToCustomerAt: OffsetDateTime?,
                additionalInformation: String,
                deliveryTime: OffsetDateTime,
                room: String,
                socialMediaPermission: Boolean,
                city: String,
                phoneNumber: String,
                deliveryAddress: String,
                homeAddress: String,
                billingAddress: String,
                isHomeBillingAddress: Boolean,
                emailAddress: String,
                customer: String,
                price: Double?,
                priceTaxFree: Double?,
                moreInformation: String,
                sinksInformation: String,
                otherProductsInformation: String,
                electricProductsInformation: String,
                domesticAppliancesInformation: String,
                intermediateSpacesInformation: String,
                doorsInformation: String,
                counterTopsInformation: String,
                handlesInformation: String,
                mechanismsInformation: String,
                creatorId: UUID): CustomerOrder {

        return orderDAO.create(
                UUID.randomUUID(), orderStatus, seenByManagerAt, sentToCustomerAt, additionalInformation, deliveryTime, room, socialMediaPermission, city, phoneNumber, deliveryAddress, homeAddress, billingAddress, isHomeBillingAddress, emailAddress, customer,price, priceTaxFree, moreInformation,
                sinksInformation, otherProductsInformation, electricProductsInformation, domesticAppliancesInformation, intermediateSpacesInformation, doorsInformation, counterTopsInformation, handlesInformation, mechanismsInformation, creatorId
        )
    }

    /**
     * Updates order details to the database
     *
     * @param customerOrder an order to updated
     * @param orderStatus order status
     * @param additionalInformation additional information
     * @param deliveryTime time of the delivery
     * @param room room
     * @param socialMediaPermission social media permission
     * @param city city
     * @param phoneNumber customer phone number
     * @param deliveryAddress delivery address
     *
     * @param homeAddress home address
     * @param billingAddress billing address
     * @param isHomeBillingAddress is home address billing address
     *
     * @param emailAddress customer email address
     * @param customer new customer name
     * @param price new price
     * @param priceTaxFree new tax free price
     * @param moreInformation a new value for moreInformation-field
     *
     * @param sinksInformation sinks additional information
     * @param otherProductsInformation other products additional information
     * @param electricProductsInformation electric products additional information
     * @param domesticAppliancesInformation domestic appliances additional information
     * @param intermediateSpacesInformation intermediate spaces additional information
     * @param mechanismsInformation mechanisms additional information
     *
     * @param counterTopsInformation counter tops information
     * @param handlesInformation handles information
     * @param doorsInformation
     *
     * @param modifierId id of the user who updates this order
     *
     * @return an updated order
     */
    fun update (customerOrder: CustomerOrder,
                orderStatus: OrderStatus,
                seenByManagerAt: OffsetDateTime?,
                sentToCustomerAt: OffsetDateTime?,
                additionalInformation: String,
                deliveryTime: OffsetDateTime,
                room: String,
                socialMediaPermission: Boolean,
                city: String,
                phoneNumber: String,
                deliveryAddress: String,
                homeAddress: String,
                billingAddress: String,
                isHomeBillingAddress: Boolean,
                emailAddress: String,
                customer: String,
                price: Double?,
                priceTaxFree: Double?,
                moreInformation: String,
                sinksInformation: String,
                otherProductsInformation: String,
                electricProductsInformation: String,
                domesticAppliancesInformation: String,
                intermediateSpacesInformation: String,
                doorsInformation: String,
                counterTopsInformation: String,
                handlesInformation: String,
                mechanismsInformation: String,
                modifierId: UUID): CustomerOrder {


        orderDAO.updateOrderStatus(customerOrder, orderStatus, modifierId)
        orderDAO.updateSeenByManagerAt(customerOrder, seenByManagerAt, modifierId)
        orderDAO.updateSentToCustomerAt(customerOrder, sentToCustomerAt, modifierId)

        orderDAO.updateAdditionalInformation(customerOrder, additionalInformation, modifierId)
        orderDAO.updateCity(customerOrder, city, modifierId)
        orderDAO.updateCustomer(customerOrder, customer, modifierId)
        orderDAO.updateDeliveryAddress(customerOrder, deliveryAddress, modifierId)
        orderDAO.updateDeliveryTime(customerOrder, deliveryTime, modifierId)
        orderDAO.updateEmailAddress(customerOrder, emailAddress, modifierId)
        orderDAO.updateRoom(customerOrder, room, modifierId)
        orderDAO.updatePhoneNumber(customerOrder, phoneNumber, modifierId)
        orderDAO.updateSocialMediaPermission(customerOrder, socialMediaPermission, modifierId)
        orderDAO.updateMoreInformation(customerOrder, moreInformation, modifierId)

        orderDAO.updatePrice(customerOrder,price, modifierId)
        orderDAO.updatePriceTaxFree(customerOrder,priceTaxFree, modifierId)

        orderDAO.updateDomesticAppliancesInformation(customerOrder, domesticAppliancesInformation, modifierId)
        orderDAO.updateSinksInformation(customerOrder, sinksInformation, modifierId)
        orderDAO.updateOtherProductsInformation(customerOrder, otherProductsInformation, modifierId)
        orderDAO.updateElectricProductsInformation(customerOrder, electricProductsInformation, modifierId)
        orderDAO.updateIntermediateSpacesInformation(customerOrder, intermediateSpacesInformation, modifierId)
        orderDAO.updateMechanismsInformation(customerOrder, mechanismsInformation, modifierId)

        orderDAO.updateHomeAddress(customerOrder, homeAddress, modifierId)
        orderDAO.updateIsHomeBillingAddress(customerOrder, isHomeBillingAddress, modifierId)
        orderDAO.updateBillingAddress(customerOrder, billingAddress, modifierId)

        orderDAO.updateCounterTopsInformation(customerOrder, counterTopsInformation, modifierId)
        orderDAO.updateHandlesInformation(customerOrder, handlesInformation, modifierId)
        orderDAO.updateDoorsInformation(customerOrder, doorsInformation, modifierId)

        return customerOrder
    }


}
