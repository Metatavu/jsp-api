package fi.metatavu.jsp.orders

import fi.metatavu.jsp.persistence.dao.ExceptionalNoteDAO
import fi.metatavu.jsp.persistence.dao.OrderDAO
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.ExceptionalNote
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
    private lateinit var exceptionalNoteDAO: ExceptionalNoteDAO

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
        val notes = listExceptionalNotes(customerOrder)

        notes.forEach { note ->
            exceptionalNoteDAO.delete(note)
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
     * @param moreInformation more information
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
                exceptionalNotes: List<String>,
                creatorId: UUID): CustomerOrder {
        val createdOrder = orderDAO.create(UUID.randomUUID(), additionalInformation, deliveryTime, room, socialMediaPermission, city, phoneNumber, deliveryAddress, emailAddress, customer, moreInformation, creatorId)

        for (note in exceptionalNotes) {
            exceptionalNoteDAO.create(UUID.randomUUID(), createdOrder, note, creatorId)
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
     * @param moreInformation a new value for moreInformation-field
     * @param modifierId id of the user who updates this order
     *
     * @return a new order
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
                exceptionalNotes: List<String>,
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

        val existingNotes = listExceptionalNotes(customerOrder)

        for (note in existingNotes) {
            exceptionalNoteDAO.delete(note)
        }

        for (note in exceptionalNotes) {
            exceptionalNoteDAO.create(UUID.randomUUID(), customerOrder, note, modifierId)
        }

        return customerOrder
    }

    /**
     * Lists exceptional notes
     *
     * @param customerOrder list only notes that belong to this order
     *
     * @return notes
     */
    fun listExceptionalNotes (customerOrder: CustomerOrder): List<ExceptionalNote> {
        return exceptionalNoteDAO.list(customerOrder)
    }
}