package fi.metatavu.jsp.products

import fi.metatavu.jsp.persistence.dao.CounterFrameDAO
import fi.metatavu.jsp.persistence.model.CounterFrame
import fi.metatavu.jsp.persistence.model.CustomerOrder
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * A controller for counter frames
 */
@ApplicationScoped
class CounterFramesController {
    @Inject
    private lateinit var counterFrameDAO: CounterFrameDAO

    /**
     * Saves a counter frame to a database
     *
     * @param customerOrder an order to which this counter frame belongs
     * @param color a counter frame color
     * @param cornerStripe a counter frame corner stripe
     * @param extraSide a counter frame extra side
     * @param plinth a counter frame plinth
     * @param additionalInformation an additional information
     * @param creatorId id of the user who is creating this counter frame
     *
     * @return created counter frame
     */
    fun create (customerOrder: CustomerOrder, color: String, cornerStripe: String, extraSide: String, plinth: String, additionalInformation: String, creatorId: UUID): CounterFrame {
        return counterFrameDAO.create(UUID.randomUUID(), customerOrder, color, cornerStripe, extraSide, plinth, additionalInformation, creatorId)
    }

    /**
     * Updates a counter frame
     *
     * @param counterFrame a counter frame to update
     * @param color a new counter frame color
     * @param cornerStripe a new counter frame corner stripe
     * @param extraSide a new counter frame extra side
     * @param plinth a new counter frame plinth
     * @param additionalInformation a new additional information
     * @param lastModifierId id of the user who is modifying this counter frame
     *
     * @return updated counter frame
     */
    fun update (counterFrame: CounterFrame, color: String, cornerStripe: String, extraSide: String, plinth: String, additionalInformation: String, lastModifierId: UUID): CounterFrame {
        counterFrameDAO.updateColor(counterFrame, color, lastModifierId)
        counterFrameDAO.updateCornerStripe(counterFrame, cornerStripe, lastModifierId)
        counterFrameDAO.updateExtraSide(counterFrame, extraSide, lastModifierId)
        counterFrameDAO.updatePlinth(counterFrame, plinth, lastModifierId)
        counterFrameDAO.updateAdditionalInformation(counterFrame, additionalInformation, lastModifierId)

        return counterFrame
    }

    /**
     * Lists counter frames
     *
     * @param customerOrder list only counter frames belonging to this order
     *
     * @return counter frames
     */
    fun list (customerOrder: CustomerOrder?): List<CounterFrame> {
        return counterFrameDAO.list(customerOrder)
    }

    /**
     * Finds a counter frame
     *
     * @param counterFrameId id of a counter frame to find
     *
     * @return found counter frame or null if not found
     */
    fun find (counterFrameId: UUID): CounterFrame? {
        return counterFrameDAO.findById(counterFrameId)
    }

    /**
     * Deletes a counter frame
     *
     * @param counterFrame a counter frame to delete
     */
    fun delete (counterFrame: CounterFrame) {
        counterFrameDAO.delete(counterFrame)
    }
}