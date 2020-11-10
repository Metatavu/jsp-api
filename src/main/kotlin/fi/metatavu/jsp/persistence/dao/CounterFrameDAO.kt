package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.CounterFrame
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.CounterFrame_
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate

@ApplicationScoped
class CounterFrameDAO: AbstractDAO<CounterFrame>() {

    /**
     * Saves a counter frame to a database
     *
     * @param id an UUID for identification
     * @param customerOrder an order to which this counter frame belongs
     * @param color counter frame color
     * @param cornerStripe counter frame corner stripe
     * @param extraSide counter frame extra side
     * @param plinth counter frame plinth
     * @param additionalInformation counter frame additional information
     * @param creatorId id of the user who is creating this counter frame
     *
     * @return created counter frame
     */
    fun create (id: UUID, customerOrder: CustomerOrder, color: String, cornerStripe: String, extraSide: String, plinth: String, additionalInformation: String, creatorId: UUID): CounterFrame {
        val counterFrame = CounterFrame()
        counterFrame.id = id
        counterFrame.customerOrder = customerOrder
        counterFrame.color = color
        counterFrame.cornerStripe = cornerStripe
        counterFrame.extraSide = extraSide
        counterFrame.plinth = plinth
        counterFrame.additionalInformation = additionalInformation
        counterFrame.creatorId = creatorId
        counterFrame.lastModifierId = creatorId

        return persist(counterFrame)
    }

    /**
     * Updates counter frame color
     *
     * @param counterFrame a counter frame to update
     * @param color a new color
     * @param lastModifierId id of the user who is modifying this counter frame
     *
     * @return updated counter frame
     */
    fun updateColor (counterFrame: CounterFrame, color: String, lastModifierId: UUID): CounterFrame {
        counterFrame.color = color
        counterFrame.lastModifierId = lastModifierId
        return persist(counterFrame)
    }

    /**
     * Updates counter frame corner stripe
     *
     * @param counterFrame a counter frame to update
     * @param cornerStripe a new corner stripe
     * @param lastModifierId id of the user who is modifying this counter frame
     *
     * @return updated counter frame
     */
    fun updateCornerStripe (counterFrame: CounterFrame, cornerStripe: String, lastModifierId: UUID): CounterFrame {
        counterFrame.cornerStripe = cornerStripe
        counterFrame.lastModifierId = lastModifierId
        return persist(counterFrame)
    }

    /**
     * Updates counter frame extra side
     *
     * @param counterFrame a counter frame to update
     * @param extraSide a new extra side
     * @param lastModifierId id of the user who is modifying this counter frame
     *
     * @return updated counter frame
     */
    fun updateExtraSide (counterFrame: CounterFrame, extraSide: String, lastModifierId: UUID): CounterFrame {
        counterFrame.extraSide = extraSide
        counterFrame.lastModifierId = lastModifierId
        return persist(counterFrame)
    }

    /**
     * Updates counter frame plinth
     *
     * @param counterFrame a counter frame to update
     * @param plinth a new plinth
     * @param lastModifierId id of the user who is modifying this counter frame
     *
     * @return updated counter frame
     */
    fun updatePlinth (counterFrame: CounterFrame, plinth: String, lastModifierId: UUID): CounterFrame {
        counterFrame.plinth = plinth
        counterFrame.lastModifierId = lastModifierId
        return persist(counterFrame)
    }

    /**
     * Updates counter frame additional information
     *
     * @param counterFrame a counter frame to update
     * @param additionalInformation a new additional information
     * @param lastModifierId id of the user who is modifying this counter frame
     *
     * @return updated counter frame
     */
    fun updateAdditionalInformation (counterFrame: CounterFrame, additionalInformation: String, lastModifierId: UUID): CounterFrame {
        counterFrame.additionalInformation = additionalInformation
        counterFrame.lastModifierId = lastModifierId
        return persist(counterFrame)
    }

    /**
     * Lists counter frames
     *
     * @param customerOrder list only counter frames belonging to this order
     *
     * @return counter frames
     */
    fun list (customerOrder: CustomerOrder?): List<CounterFrame> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(CounterFrame::class.java)
        val root = criteria.from(CounterFrame::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()

        if (customerOrder != null) {
            restrictions.add(criteriaBuilder.equal(root.get(CounterFrame_.customerOrder), customerOrder))
        }

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }

}
