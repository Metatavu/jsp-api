package fi.metatavu.jsp.persistence.model

import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

/**
 * JPA entity representing Product
 *
 * @author Antti Leinonen
 */
@Entity @Table(name = "product")
class Product {

    @Id
    var id: UUID? = null

    var name: String? = null

    var description: String? = null

    @ManyToMany
    @JoinColumn(name = "id")
    var images: MutableList<Image> = mutableListOf()

    @ManyToMany
    @JoinColumn(name = "id")
    var tags: MutableList<Tag> = mutableListOf()

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