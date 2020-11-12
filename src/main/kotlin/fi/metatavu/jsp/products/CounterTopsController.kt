package fi.metatavu.jsp.products

import fi.metatavu.jsp.api.spec.model.CounterTopType
import fi.metatavu.jsp.persistence.dao.CounterTopDAO
import fi.metatavu.jsp.persistence.model.CounterTop
import fi.metatavu.jsp.persistence.model.CustomerOrder
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * A controller for counter tops
 */
@ApplicationScoped
class CounterTopsController {
    @Inject
    private lateinit var counterTopDAO: CounterTopDAO

    /**
     * Saves a counter top to database
     *
     * @param customerOrder an order to which this counter top belongs
     * @param counterTopType type of a new counter top
     * @param modelName model name of a new counter top
     * @param thickness thickness of a new counter top
     * @param creatorId id of the user who is creating this counter top
     *
     * @return a new counter top
     */
    fun create (customerOrder: CustomerOrder, counterTopType: CounterTopType, modelName: String, thickness: String, creatorId: UUID): CounterTop {
        return counterTopDAO.create(UUID.randomUUID(), customerOrder, counterTopType, modelName, thickness, creatorId)
    }

    /**
     * Updates counter top
     *
     * @param counterTop a counter top to update
     * @param counterTopType a new type
     * @param modelName a new model name
     * @param thickness a new thickness
     * @param lastModifierId id of the user who is modifying this counter top
     *
     * @return a new counter top
     */
    fun update (counterTop: CounterTop, counterTopType: CounterTopType, modelName: String, thickness: String, lastModifierId: UUID): CounterTop {
        counterTopDAO.updateCounterTopType(counterTop, counterTopType, lastModifierId)
        counterTopDAO.updateModelName(counterTop, modelName, lastModifierId)
        counterTopDAO.updateThickness(counterTop, thickness, lastModifierId)

        return counterTop
    }

    /**
     * Lists counter tops
     *
     * @param customerOrder list only counter tops belonging to this order
     *
     * @return counter tops
     */
    fun list (customerOrder: CustomerOrder?): List<CounterTop> {
        return counterTopDAO.list(customerOrder)
    }

    /**
     * Finds a counter top
     *
     * @param counterTopId the id of a counter top to find
     *
     * @return found counter top or null if not found
     */
    fun find (counterTopId: UUID): CounterTop? {
        return counterTopDAO.findById(counterTopId)
    }

    /**
     * Deletes a counter top
     *
     * @param counterTop a counter top to delete
     */
    fun delete (counterTop: CounterTop) {
        counterTopDAO.delete(counterTop)
    }
}