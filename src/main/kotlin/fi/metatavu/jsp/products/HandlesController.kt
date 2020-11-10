package fi.metatavu.jsp.products

import fi.metatavu.jsp.persistence.dao.HandleDAO
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.Handle
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * A controller for handles
 */
@ApplicationScoped
class HandlesController {

    @Inject
    private lateinit var handleDAO: HandleDAO

    /**
     * Saves a handle to a database
     *
     * @param doorModelName a door model name
     * @param color a handle color
     * @param isMarkedInImages is handle marked in images
     * @param customerOrder an order to which this handle belongs to
     * @param creatorId id of the user who is creating this handle
     *
     * @return created handle
     */
    fun create (doorModelName: String, color: String, isMarkedInImages: Boolean, customerOrder: CustomerOrder, creatorId: UUID): Handle {
        return handleDAO.create(UUID.randomUUID(), doorModelName, color, isMarkedInImages, customerOrder, creatorId)
    }

    /**
     * Updates a handle
     *
     * @param handle a handle to update
     * @param doorModelName a new door model name
     * @param color a new handle color
     * @param isMarkedInImages is handle marked in images
     * @param lastModifierId id of the user who is modifying this handle
     *
     * @return updated handle
     */
    fun update (handle: Handle, doorModelName: String, color: String, isMarkedInImages: Boolean, lastModifierId: UUID): Handle {
        handleDAO.updateColor(handle, color, lastModifierId)
        handleDAO.updateDoorModelName(handle, doorModelName, lastModifierId)
        handleDAO.updateIsMarkedInImages(handle, isMarkedInImages, lastModifierId)

        return handle
    }

    /**
     * Lists handles
     *
     * @param customerOrder list only handles belonging to this order
     *
     * @return handles
     */
    fun list (customerOrder: CustomerOrder?): List<Handle> {
        return handleDAO.list(customerOrder)
    }

    /**
     * Finds a handle
     *
     * @param handleId the id of a handle to find
     *
     * @return found handle or null if not found
     */
    fun find (handleId: UUID): Handle? {
        return handleDAO.findById(handleId)
    }

    /**
     * Deletes a handle
     *
     * @param handle a handle to delete
     */
    fun delete (handle: Handle) {
        handleDAO.delete(handle)
    }
}
