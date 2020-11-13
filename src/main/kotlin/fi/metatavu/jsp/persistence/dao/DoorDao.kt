package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.Door
import fi.metatavu.jsp.persistence.model.Door_
import fi.metatavu.jsp.persistence.model.Handle_

import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate
import kotlin.collections.ArrayList

/**
 * A DAO-class for door's info
 */
@ApplicationScoped
class DoorDao: AbstractDAO<Door>() {

    /**
     * Saves a new door's info to the database
     *
     * @param id An UUID for identification
     * @param customerOrder order that this door is related to
     * @param isGlassDoor shows if door is made glass or not
     * @param glassColor glass color
     * @param modelName model name od the door
     * @param creatorId id of the user who is creating this door
     */
    fun create (id: UUID, customerOrder: CustomerOrder, isGlassDoor: Boolean, glassColor: String, modelName: String, doorColor: String, creatorId: UUID): Door {
        val door = Door()
        door.id = id
        door.customerOrder = customerOrder
        door.isglassdoor = isGlassDoor
        door.glasscolor = glassColor
        door.modelName = modelName
        door.doorColor = doorColor
        door.creatorId = creatorId
        door.lastModifierId = creatorId
        return persist(door)
    }

    /**
     * Updates if door is made of glass
     *
     * @param door door to update
     * @param isGlassDoor new value to show if door is glass or not
     *
     * @return an updated door's info
     */
    fun updateGlassdoor (door: Door, isGlassDoor: Boolean, modifierId: UUID): Door {
        door.isglassdoor = isGlassDoor
        door.lastModifierId = modifierId
        return persist(door)
    }

    /**
     * Updates door's glasscolor
     *
     * @param door door to update
     * @param glassColor new value to door's glass color
     *
     * @return an updated door's info
     */
    fun updateGlassColor (door: Door, glassColor: String, modifierId: UUID): Door {
        door.glasscolor = glassColor
        door.lastModifierId = modifierId
        return persist(door)
    }

    /**
     * Updates door's model name
     *
     * @param door door to update
     * @param modelName new value to door's model name
     *
     * @return an updated door's info
     */
    fun updateModelName (door: Door, modelName: String, modifierId: UUID): Door {
        door.modelName = modelName
        door.lastModifierId = modifierId
        return persist(door)
    }

    /**
     * Updates door's model name
     *
     * @param door door to update
     * @param doorColor new value to door's model name
     *
     * @return an updated door's info
     */
    fun updateDoorColor (door: Door, doorColor: String, modifierId: UUID): Door {
        door.doorColor = doorColor
        door.lastModifierId = modifierId
        return persist(door)
    }

    /**
     * Lists Doors
     *
     * @param customerOrder list only doors that belong to this order
     *
     * @return list of doors
     */
    fun list (customerOrder: CustomerOrder?): List<Door> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(Door::class.java)
        val root = criteria.from(Door::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()

        if (customerOrder != null) {
            restrictions.add(criteriaBuilder.equal(root.get(Door_.customerOrder), customerOrder))
        }

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}