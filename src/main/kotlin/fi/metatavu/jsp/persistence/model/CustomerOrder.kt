package fi.metatavu.jsp.persistence.model

import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Entity
class CustomerOrder {
    @Id
    var id: UUID? = null

    @Column(nullable = false)
    var additionalInformation: String? = null

    @Column(nullable = false)
    var deliveryTime: OffsetDateTime? = null

    @Column(nullable = false)
    var room: String? = null

    @Column(nullable = false)
    var socialMediaPermission: Boolean? = null

    @Column(nullable = false)
    var city: String? = null

    @Column(nullable = false)
    var phoneNumber: String? = null

    @Column(nullable = false)
    var deliveryAddress: String? = null

    @Column(nullable = false)
    var email: String? = null

    @Column(nullable = false)
    var customer: String? = null

    @Column(nullable = false)
    var moreInformation: String? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var modifiedAt: OffsetDateTime? = null

    @Column(nullable = false)
    var creatorId: UUID? = null

    @Column(nullable = false)
    var lastModifierId: UUID? = null

    /**
     * JPA pre-persist event handler
     */
    @PrePersist
    fun onCreate() {
        createdAt = OffsetDateTime.now()
        modifiedAt = OffsetDateTime.now()
    }

    /**
     * JPA pre-update event handler
     */
    @PreUpdate
    fun onUpdate() {
        modifiedAt = OffsetDateTime.now()
    }
}