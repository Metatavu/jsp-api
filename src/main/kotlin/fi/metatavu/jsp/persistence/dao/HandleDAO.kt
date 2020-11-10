package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.Handle_
import fi.metatavu.jsp.persistence.model.Handle
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate

@ApplicationScoped
class HandleDAO: AbstractDAO<Handle>() {
    /**
     * Saves a handle to a database
     *
     * @param id An UUID for identification
     * @param doorModelName a door model name
     * @param color a handle color
     * @param isMarkedInImages is handle marked in images
     * @param customerOrder an order to which this handle belongs to
     * @param creatorId id of the user who is creating this handle
     *
     * @return created handle
     */
    fun create (id: UUID, doorModelName: String, color: String, isMarkedInImages: Boolean, customerOrder: CustomerOrder, creatorId: UUID): Handle {
        val handle = Handle()
        handle.id = id
        handle.doorModelName = doorModelName
        handle.color = color
        handle.isMarkedInImages = isMarkedInImages
        handle.creatorId = creatorId
        handle.lastModifierId = creatorId
        handle.customerOrder = customerOrder

        return persist(handle)
    }

    /**
     * Updates a door model name
     *
     * @param handle a handle to update
     * @param doorModelName a new door model name
     * @param lastModifierId id of the user who is modifying this handle
     *
     * @return updated handle
     */
    fun updateDoorModelName (handle: Handle, doorModelName: String, lastModifierId: UUID): Handle {
        handle.doorModelName = doorModelName
        handle.lastModifierId = lastModifierId
        return persist(handle)
    }

    /**
     * Updates a handle color
     *
     * @param handle a handle to update
     * @param color a handle color
     * @param lastModifierId id of the user who is modifying this handle
     *
     * @return updated handle
     */
    fun updateColor(handle: Handle, color: String, lastModifierId: UUID): Handle {
        handle.color = color
        handle.lastModifierId = lastModifierId
        return persist(handle)
    }

    /**
     * Updates a value for isMarkedInImages
     *
     * @param handle a handle to update
     * @param isMarkedInImages a new value for isMarkedInImages
     * @param lastModifierId id of the user who is modifying this handle
     *
     * @return updated handle
     */
    fun updateIsMarkedInImages(handle: Handle, isMarkedInImages: Boolean, lastModifierId: UUID): Handle {
        handle.isMarkedInImages = isMarkedInImages
        handle.lastModifierId = lastModifierId
        return persist(handle)
    }

    /**
     * Lists handles
     *
     * @param customerOrder list only handles belonging to this order
     *
     * @return handles
     */
    fun list (customerOrder: CustomerOrder?): List<Handle> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(Handle::class.java)
        val root = criteria.from(Handle::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()

        if (customerOrder != null) {
            restrictions.add(criteriaBuilder.equal(root.get(Handle_.customerOrder), customerOrder))
        }

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}