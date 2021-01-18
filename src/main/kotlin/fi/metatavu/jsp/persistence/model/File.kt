package fi.metatavu.jsp.persistence.model

import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Entity
class File {

    @Id
    var id: UUID? = null

    @ManyToOne(optional = false)
    var customerOrder: CustomerOrder? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var modifiedAt: OffsetDateTime? = null

    @Column(nullable = false)
    var creatorId: UUID? = null

    @Column(nullable = false)
    var lastModifierId: UUID? = null

    @Column(nullable = false)
    var customerFile: Boolean? = null

    @Column(nullable = false)
    var name: String? = null

    @Column(nullable = false)
    var url: String? = null

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
