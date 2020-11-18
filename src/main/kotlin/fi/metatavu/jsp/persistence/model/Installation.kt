package fi.metatavu.jsp.persistence.model

import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Entity
class Installation {

    @Id
    var id: UUID? = null

    @ManyToOne(optional = false)
    var customerOrder: CustomerOrder? = null

    @Column(nullable = false)
    var additionalInformation: String? = null

    @Column(nullable = false)
    var isCustomerInstallation: Boolean? = null

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

