package fi.metatavu.jsp.orders

import fi.metatavu.jsp.persistence.dao.ExceptionFromPlansDAO
import fi.metatavu.jsp.persistence.dao.OrderDAO
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.ExceptionFromPlans
import fi.metatavu.jsp.products.GenericProductsController
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
    private lateinit var exceptionFromPlansDAO: ExceptionFromPlansDAO

    @Inject
    private lateinit var genericProductsController: GenericProductsController

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
        val notes = listExceptionsFromPlans(customerOrder)

        val products = genericProductsController.list(null, customerOrder)

        products.forEach { product ->
            genericProductsController.delete(product)
        }
        
        notes.forEach { note ->
            exceptionFromPlansDAO.delete(note)
        }

        return orderDAO.delete(customerOrder)
    }

    /**
     * Saves a new order to the database
     *
     * @param additionalInformation additional information
     * @param deliveryTime time of the delivery
     * @param room room
     * @param socialMediaPermission social media permission
     * @param city city
     * @param phoneNumber customer phone number
     * @param deliveryAddress delivery address
     * @param emailAddress customer email address
     * @param customer customer name
     * @param moreInformation more information
     * @param exceptionsFromPlans exceptions from plans
     * @param creatorId id of the user who creates this order
     *
     * @return a new order
     */
    fun create (additionalInformation: String,
                deliveryTime: OffsetDateTime,
                room: String,
                socialMediaPermission: Boolean,
                city: String,
                phoneNumber: String,
                deliveryAddress: String,
                emailAddress: String,
                customer: String,
                moreInformation: String,
                exceptionsFromPlans: List<String>,
                creatorId: UUID): CustomerOrder {
        val createdOrder = orderDAO.create(UUID.randomUUID(), additionalInformation, deliveryTime, room, socialMediaPermission, city, phoneNumber, deliveryAddress, emailAddress, customer, moreInformation, creatorId)

        for (note in exceptionsFromPlans) {
            exceptionFromPlansDAO.create(UUID.randomUUID(), createdOrder, note, creatorId)
        }

        return createdOrder
    }

    /**
     * Updates order details to the database
     *
     * @param customerOrder an order to updated
     * @param additionalInformation additional information
     * @param deliveryTime time of the delivery
     * @param room room
     * @param socialMediaPermission social media permission
     * @param city city
     * @param phoneNumber customer phone number
     * @param deliveryAddress delivery address
     * @param emailAddress customer email address
     * @param customer new customer name
     * @param moreInformation a new value for moreInformation-field
     * @param exceptionsFromPlans exceptions from plans
     * @param modifierId id of the user who updates this order
     *
     * @return an updated order
     */
    fun update (customerOrder: CustomerOrder,
                additionalInformation: String,
                deliveryTime: OffsetDateTime,
                room: String,
                socialMediaPermission: Boolean,
                city: String,
                phoneNumber: String,
                deliveryAddress: String,
                emailAddress: String,
                customer: String,
                moreInformation: String,
                exceptionsFromPlans: List<String>,
                modifierId: UUID): CustomerOrder {

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

        val existingNotes = listExceptionsFromPlans(customerOrder)

        for (note in existingNotes) {
            exceptionFromPlansDAO.delete(note)
        }

        for (note in exceptionsFromPlans) {
            exceptionFromPlansDAO.create(UUID.randomUUID(), customerOrder, note, modifierId)
        }

        return customerOrder
    }

    /**
     * Lists exception notes
     *
     * @param customerOrder list only notes that belong to this order
     *
     * @return notes
     */
    fun listExceptionsFromPlans (customerOrder: CustomerOrder): List<ExceptionFromPlans> {
        return exceptionFromPlansDAO.list(customerOrder)
    }
}
