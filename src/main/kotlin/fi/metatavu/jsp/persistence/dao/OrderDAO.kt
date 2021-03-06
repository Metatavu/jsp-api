package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.api.spec.model.OrderStatus
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
     * @param orderStatus order status
     * @param seenByManagerAt: seen by manager timestamp,
     * @param sentToCustomerAt: sent to customer at timestamp
     * @param additionalInformation additional information
     * @param deliveryTime time of the delivery
     * @param room room
     * @param socialMediaPermission social media permission
     * @param city city
     * @param phoneNumber customer phone number
     * @param deliveryAddress delivery address
     * @param homeAddress home address
     * @param billingAddress billing address
     * @param isHomeBillingAddress is home address billing address
     * @param emailAddress customer email address
     * @param customer customer name
     * @param price: price
     * @param priceTaxFree: priceTaxFree
     * @param moreInformation more information
     *
     * @param sinksInformation sinks additional information
     * @param otherProductsInformation other products additional information
     * @param electricProductsInformation electric products additional information
     * @param domesticAppliancesInformation domestic appliances additional information
     * @param intermediateSpacesInformation intermediate spaces additional information
     * @param doorsInformation doors additional information
     * @param mechanismsInformation mechanisms additional information
     *
     * @param counterTopsInformation counter tops information
     * @param handlesInformation handles information
     *
     * @param creatorId id of the user who creates this order
     *
     * @return a new order
     */
    fun create (id: UUID,
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
                creatorId: UUID): CustomerOrder {

        val order = CustomerOrder()
        order.id = id
        order.orderStatus = orderStatus
        order.seenByManagerAt = seenByManagerAt
        order.sentToCustomerAt = sentToCustomerAt
        order.additionalInformation = additionalInformation
        order.deliveryTime = deliveryTime
        order.room = room
        order.socialMediaPermission = socialMediaPermission
        order.city = city
        order.phoneNumber = phoneNumber
        order.deliveryAddress = deliveryAddress
        order.homeAddress = homeAddress
        order.billingAddress = billingAddress
        order.isHomeBillingAddress = isHomeBillingAddress
        order.email = emailAddress
        order.customer = customer
        order.price = price
        order.priceTaxFree = priceTaxFree

        order.creatorId = creatorId
        order.moreInformation = moreInformation

        order.intermediateSpacesInformation = intermediateSpacesInformation
        order.domesticAppliancesInformation = domesticAppliancesInformation
        order.electricProductsInformation = electricProductsInformation
        order.sinksInformation = sinksInformation
        order.otherProductsInformation = otherProductsInformation
        order.doorsInformation = doorsInformation
        order.mechanismsInformation = mechanismsInformation


        order.counterTopsInformation = counterTopsInformation
        order.handlesInformation = handlesInformation

        order.lastModifierId = creatorId

        return persist(order)
    }

    /**
     * Updates order status
     *
     * @param customerOrder an order to be updated
     * @param orderStatus new status
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateOrderStatus (customerOrder: CustomerOrder, orderStatus: OrderStatus, modifierId: UUID): CustomerOrder {
        customerOrder.orderStatus = orderStatus
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates order seen by manager at time stamp
     *
     * @param customerOrder an order to be updated
     * @param seenByManagerAt new timestamp
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateSeenByManagerAt (customerOrder: CustomerOrder, seenByManagerAt: OffsetDateTime?, modifierId: UUID): CustomerOrder {
        customerOrder.seenByManagerAt = seenByManagerAt
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates order sent to customer at time stamp
     *
     * @param customerOrder an order to be updated
     * @param sentToCustomerAt new timestamp
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateSentToCustomerAt (customerOrder: CustomerOrder, sentToCustomerAt: OffsetDateTime?, modifierId: UUID): CustomerOrder {
        customerOrder.seenByManagerAt = sentToCustomerAt
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }
    /**
     * Updates intermediate spaces information
     *
     * @param customerOrder an order to be updated
     * @param additionalInformation a new additional information
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateIntermediateSpacesInformation (customerOrder: CustomerOrder, additionalInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.intermediateSpacesInformation = additionalInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates domestic appliances information
     *
     * @param customerOrder an order to be updated
     * @param additionalInformation a new additional information
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateDomesticAppliancesInformation (customerOrder: CustomerOrder, additionalInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.domesticAppliancesInformation = additionalInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates electric products information
     *
     * @param customerOrder an order to be updated
     * @param additionalInformation a new additional information
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateElectricProductsInformation (customerOrder: CustomerOrder, additionalInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.electricProductsInformation = additionalInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates other products information
     *
     * @param customerOrder an order to be updated
     * @param additionalInformation a new additional information
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateOtherProductsInformation (customerOrder: CustomerOrder, additionalInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.otherProductsInformation = additionalInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates sinks information
     *
     * @param customerOrder an order to be updated
     * @param additionalInformation a new additional information
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateSinksInformation (customerOrder: CustomerOrder, additionalInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.sinksInformation = additionalInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates mechanisms information
     *
     * @param customerOrder an order to be updated
     * @param additionalInformation a new additional information
     * @param modifierId id od the user who modifies this order
     */
    fun updateMechanismsInformation (customerOrder: CustomerOrder, additionalInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.mechanismsInformation = additionalInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
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
     * Updates doors information
     *
     * @param customerOrder an order to be updated
     * @param doorInformation new additional information
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateDoorsInformation(customerOrder: CustomerOrder, doorInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.doorsInformation = doorInformation
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
     * Updates home address
     *
     * @param customerOrder an order to be updated
     * @param homeAddress a new home address
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateHomeAddress (customerOrder: CustomerOrder, homeAddress: String, modifierId: UUID): CustomerOrder {
        customerOrder.homeAddress = homeAddress
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates billing address
     *
     * @param customerOrder an order to be updated
     * @param billingAddress a new billing address
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateBillingAddress (customerOrder: CustomerOrder, billingAddress: String, modifierId: UUID): CustomerOrder {
        customerOrder.billingAddress = billingAddress
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates a value for isHomeBillingAddress
     *
     * @param customerOrder an order to be updated
     * @param isHomeBillingAddress a new boolean value
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateIsHomeBillingAddress (customerOrder: CustomerOrder, isHomeBillingAddress: Boolean, modifierId: UUID): CustomerOrder {
        customerOrder.isHomeBillingAddress = isHomeBillingAddress
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

    /**
     * Updates price
     *
     * @param customerOrder an order to be updated
     * @param price updated price
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updatePrice(customerOrder: CustomerOrder, price: Double?, modifierId: UUID): CustomerOrder {
        customerOrder.price = price
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates price
     *
     * @param customerOrder an order to be updated
     * @param priceTaxFree updated tax free price
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updatePriceTaxFree(customerOrder: CustomerOrder, priceTaxFree: Double?, modifierId: UUID): CustomerOrder {
        customerOrder.priceTaxFree = priceTaxFree
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates counter tops information field
     *
     * @param customerOrder an order to be updated
     * @param counterTopsInformation a new value for counterTopsInformation-field
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateCounterTopsInformation (customerOrder: CustomerOrder, counterTopsInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.counterTopsInformation = counterTopsInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }

    /**
     * Updates handles information field
     *
     * @param customerOrder an order to be updated
     * @param handlesInformation a new value for handlesInformation-field
     * @param modifierId of the user who modifies this order
     *
     * @return an updated order
     */
    fun updateHandlesInformation (customerOrder: CustomerOrder, handlesInformation: String, modifierId: UUID): CustomerOrder {
        customerOrder.handlesInformation = handlesInformation
        customerOrder.lastModifierId = modifierId
        return persist(customerOrder)
    }
}
