package fi.metatavu.jsp.persistence.model

import fi.metatavu.jsp.api.spec.model.GenericProductType
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Entity
class GenericProduct {
    @Id
    var id: UUID? = null

    @ManyToOne(optional = false)
    var customerOrder: CustomerOrder? = null

    @Column(nullable=false)
    var supplier: String? = null

    @Column(nullable=false)
    var productName: String? = null

    @Column(nullable=false)
    var productType: GenericProductType? = null

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