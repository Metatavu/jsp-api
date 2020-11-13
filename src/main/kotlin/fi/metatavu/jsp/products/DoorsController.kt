package fi.metatavu.jsp.products

import fi.metatavu.jsp.persistence.dao.DoorDao
import fi.metatavu.jsp.persistence.dao.OrderDAO
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.Door
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * A controller for doors
 */
@ApplicationScoped
class DoorsController {

    @Inject
    private lateinit var doorDao: DoorDao

    /**
     * Saves a new door's info to the database
     *
     * @param customerOrder order that this door is related to
     * @param isGlassDoor shows if door is made glass or not
     * @param glassColor glass color
     * @param modelName model name of the door
     * @param creatorId id of the user who is creating this door
     * @param doorColor
     *
     * @return a created door
     */
    fun create (customerOrder: CustomerOrder, isGlassDoor: Boolean, glassColor: String, modelName: String, doorColor: String, creatorId: UUID): Door {
        return doorDao.create(UUID.randomUUID(), customerOrder, isGlassDoor, glassColor, modelName, doorColor, creatorId)
    }

    /**
     * Updates a product
     *
     * @param door a door to update
     * @param isGlassDoor new value to show if door is made of glass or not
     * @param glassColor new value to glass color
     * @param modelName new value to model name
     * @param doorColor new value to door color
     * @param lastModifierId
     *
     * @return an updated door
     */
    fun update (door: Door, isGlassDoor: Boolean, glassColor: String, modelName: String, doorColor: String, lastModifierId: UUID): Door {
        doorDao.updateGlassdoor(door, isGlassDoor, lastModifierId)
        doorDao.updateGlassColor(door, glassColor, lastModifierId)
        doorDao.updateModelName(door, modelName, lastModifierId)
        doorDao.updateDoorColor(door, doorColor, lastModifierId)
        return door
    }


    /**
     * Lists all doors
     *
     * @param customerOrder list only products belonging to this order
     *
     * @return doors
     */
    fun list(customerOrder: CustomerOrder?): List<Door> {
        return doorDao.list(customerOrder)
    }

    /**
     * Finds all doors
     *
     * @param doorId id of the door to find
     *
     * @return found door or null if not found
     */
    fun find (doorId: UUID): Door? {
        return doorDao.findById(doorId)
    }

    /**
     * Deletes a door
     *
     * @param customerOrder door to be deleted
     */
    fun delete (door: Door) {
        doorDao.delete(door)
    }
}
