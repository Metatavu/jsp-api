package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.ExceptionFromPlans
import fi.metatavu.jsp.persistence.model.ExceptionFromPlans_
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate

/**
 * A DAO-class for exceptional notes
 */
@ApplicationScoped
class ExceptionFromPlansDAO: AbstractDAO<ExceptionFromPlans>() {
    /**
     * Saves a new exception note to the database
     *
     * @param id An UUID for identification
     * @param customerOrder order that this note is related to
     * @param note note
     * @param creatorId id of the user who is creating this note
     *
     * @return a new note
     */
    fun create (id: UUID, customerOrder: CustomerOrder, note: String, creatorId: UUID): ExceptionFromPlans {
        val exceptionalNote = ExceptionFromPlans()
        exceptionalNote.id = id
        exceptionalNote.customerOrder = customerOrder
        exceptionalNote.note = note
        exceptionalNote.lastModifierId = creatorId
        exceptionalNote.creatorId = creatorId
        return persist(exceptionalNote)
    }

    /**
     * Updates an exception note
     *
     * @param exceptionFromPlans a note to update
     * @param note a new note value
     * @param modifierId id of the user who is updating this note
     *
     * @return an update note
     */
    fun updateNote (exceptionFromPlans: ExceptionFromPlans, note: String, modifierId: UUID): ExceptionFromPlans {
        exceptionFromPlans.note = note
        exceptionFromPlans.lastModifierId = modifierId
        return persist(exceptionFromPlans)
    }

    /**
     * Lists exception notes
     *
     * @param customerOrder list only notes that belong to this order
     *
     * @return list of notes
     */
    fun list (customerOrder: CustomerOrder): List<ExceptionFromPlans> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(ExceptionFromPlans::class.java)
        val root = criteria.from(ExceptionFromPlans::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()
        restrictions.add(criteriaBuilder.equal(root.get(ExceptionFromPlans_.customerOrder), customerOrder))
        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}
