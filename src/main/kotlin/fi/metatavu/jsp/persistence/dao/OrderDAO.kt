package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.CustomerOrder
import java.time.OffsetDateTime
import java.util.*
import javax.enterprise.context.ApplicationScoped

/**
 * A DAO class for orders
 */
@ApplicationScoped
class OrderDAO: AbstractDAO<CustomerOrder>() {
    /**
     * Saves a new order to the database
     *
     * @param id an id for identification
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
    fun create (id: UUID,
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
                creatorId: UUID): CustomerOrder {

        val order = CustomerOrder()
        order.id = id
        order.additionalInformation = additionalInformation
        order.deliveryTime = deliveryTime
        order.room = room
        order.socialMediaPermission = socialMediaPermission
        order.city = city
        order.phoneNumber = phoneNumber
        order.deliveryAddress = deliveryAddress
        order.email = emailAddress
        order.customer = customer
        order.creatorId = creatorId
        order.moreInformation = moreInformation
        order.lastModifierId = creatorId

        return persist(order)
    }

    /**
     * Updates additional information
     *
     * @param customerOrder an order to be updated
     * @param additionalInformation a new additional information
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateAdditionalInformation (customerOrder: CustomerOrder, additionalInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.additionalInformation = additionalInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates delivery time
     *
     * @param customerOrder an order to be updated
     * @param deliveryTime a new delivery time
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateDeliveryTime (customerOrder: CustomerOrder, deliveryTime: OffsetDateTime, modifierId: UUID): CustomerOrder {
        customerOrder.deliveryTime = deliveryTime
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates room
     *
     * @param customerOrder an order to be updated
     * @param room a new room
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateRoom (customerOrder: CustomerOrder, room: String, modifierId: UUID): CustomerOrder {
        customerOrder.room = room
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates social media permission
     *
     * @param customerOrder an order to be updated
     * @param socialMediaPermission a new social media permission
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateSocialMediaPermission (customerOrder: CustomerOrder, socialMediaPermission: Boolean, modifierId: UUID): CustomerOrder {
        customerOrder.socialMediaPermission = socialMediaPermission
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates city
     *
     * @param customerOrder an order to be updated
     * @param city a new city
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateCity (customerOrder: CustomerOrder, city: String, modifierId: UUID): CustomerOrder {
        customerOrder.city = city
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates phone number
     *
     * @param customerOrder an order to be updated
     * @param phoneNumber a new phone number
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updatePhoneNumber (customerOrder: CustomerOrder, phoneNumber: String, modifierId: UUID): CustomerOrder {
        customerOrder.phoneNumber = phoneNumber
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates delivery address
     *
     * @param customerOrder an order to be updated
     * @param deliveryAddress a new delivery address
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateDeliveryAddress (customerOrder: CustomerOrder, deliveryAddress: String, modifierId: UUID): CustomerOrder {
        customerOrder.deliveryAddress = deliveryAddress
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates email address
     *
     * @param customerOrder an order to be updated
     * @param emailAddress a new email address
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateEmailAddress (customerOrder: CustomerOrder, emailAddress: String, modifierId: UUID): CustomerOrder {
        customerOrder.email = emailAddress
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates customer
     *
     * @param customerOrder an order to be updated
     * @param customer a new customer
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateCustomer (customerOrder: CustomerOrder, customer: String, modifierId: UUID): CustomerOrder {
        customerOrder.customer = customer
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates more information field
     *
     * @param customerOrder an order to be updated
     * @param moreInformation a new value for moreInformation-field
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateMoreInformation (customerOrder: CustomerOrder, moreInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.moreInformation = moreInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }
}