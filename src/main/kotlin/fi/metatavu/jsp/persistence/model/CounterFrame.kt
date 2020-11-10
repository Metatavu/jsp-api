package fi.metatavu.jsp.persistence.model

import fi.metatavu.jsp.api.spec.model.GenericProductType
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Entity
class CounterFrame {
    @Id
    var id: UUID? = null

    @OneToOne(optional = false)
    var customerOrder: CustomerOrder? = null

    @Column(nullable=false)
    var color: String? = null

    @Column(nullable=false)
    var cornerStripe: String? = null

    @Column(nullable=false)
    var extraSide: String? = null

    @Column(nullable=false)
    var plinth: String? = null

    @Column(nullable=false)
    var additionalInformation: String? = null

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