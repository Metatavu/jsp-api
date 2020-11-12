package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.api.spec.model.CounterTopType
import fi.metatavu.jsp.persistence.model.CounterTop
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.CounterTop_
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate

/**
 * A DAO class for counter tops
 */
@ApplicationScoped
class CounterTopDAO: AbstractDAO<CounterTop>() {
    /**
     * Saves a counter top to database
     *
     * @param id an UUID for identification
     * @param customerOrder an order to which this counter top belongs
     * @param counterTopType type of a new counter top
     * @param modelName model name of a new counter top
     * @param thickness thickness of a new counter top
     * @param creatorId id of the user who is creating this counter top
     *
     * @return a new counter top
     */
    fun create (id: UUID, customerOrder: CustomerOrder, counterTopType: CounterTopType, modelName: String, thickness: String, creatorId: UUID): CounterTop {
        val counterTop = CounterTop()
        counterTop.id = id
        counterTop.customerOrder = customerOrder
        counterTop.counterTopType = counterTopType
        counterTop.modelName = modelName
        counterTop.thickness = thickness
        counterTop.creatorId = creatorId
        counterTop.lastModifierId = creatorId

        return persist(counterTop)
    }

    /**
     * Updates the type of a counter top
     *
     * @param counterTop a counter top to update
     * @param counterTopType a new type
     * @param lastModifierId id of the user who is modifying this counter top
     *
     * @return an updated counter top
     */
    fun updateCounterTopType (counterTop: CounterTop, counterTopType: CounterTopType, lastModifierId: UUID): CounterTop {
        counterTop.counterTopType = counterTopType
        counterTop.lastModifierId = lastModifierId
        return persist(counterTop)
    }

    /**
     * Updates the model name of a counter top
     *
     * @param counterTop a counter top to update
     * @param modelName a new model name
     * @param lastModifierId id of the user who is modifying this counter top
     *
     * @return an updated counter top
     */
    fun updateModelName (counterTop: CounterTop, modelName: String, lastModifierId: UUID): CounterTop {
        counterTop.modelName = modelName
        counterTop.lastModifierId = lastModifierId
        return persist(counterTop)
    }

    /**
     * Updates the thickness of a counter top
     *
     * @param counterTop a counter top to update
     * @param thickness a new thickness
     * @param lastModifierId id of the user who is modifying this counter top
     *
     * @return an updated counter top
     */
    fun updateThickness (counterTop: CounterTop, thickness: String, lastModifierId: UUID): CounterTop {
        counterTop.thickness = thickness
        counterTop.lastModifierId = lastModifierId
        return persist(counterTop)
    }

    /**
     * Lists counter tops
     *
     * @param customerOrder list only counter tops belonging to this order
     *
     * @return counter tops
     */
    fun list (customerOrder: CustomerOrder?): List<CounterTop> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(CounterTop::class.java)
        val root = criteria.from(CounterTop::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()

        if (customerOrder != null) {
            restrictions.add(criteriaBuilder.equal(root.get(CounterTop_.customerOrder), customerOrder))
        }

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}