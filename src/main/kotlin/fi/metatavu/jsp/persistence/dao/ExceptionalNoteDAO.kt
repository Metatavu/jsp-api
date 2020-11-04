package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.ExceptionalNote
import fi.metatavu.jsp.persistence.model.ExceptionalNote_
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate

/**
 * A DAO-class for exceptional notes
 */
@ApplicationScoped
class ExceptionalNoteDAO: AbstractDAO<ExceptionalNote>() {
    /**
     * Saves a new exceptional note to the database
     *
     * @param id An UUID for identification
     * @param customerOrder order that this note is related to
     * @param note note
     * @param creatorId id of the user who is creating this note
     *
     * @return a new note
     */
    fun create (id: UUID, customerOrder: CustomerOrder, note: String, creatorId: UUID): ExceptionalNote {
        val exceptionalNote = ExceptionalNote()
        exceptionalNote.id = id
        exceptionalNote.customerOrder = customerOrder
        exceptionalNote.note = note
        exceptionalNote.lastModifierId = creatorId
        exceptionalNote.creatorId = creatorId
        return persist(exceptionalNote)
    }

    /**
     * Updates an exceptional note
     *
     * @param exceptionalNote a note to update
     * @param note a new note value
     * @param modifierId id of the user who is updating this note
     *
     * @return an update note
     */
    fun updateNote (exceptionalNote: ExceptionalNote, note: String, modifierId: UUID): ExceptionalNote {
        exceptionalNote.note = note
        exceptionalNote.lastModifierId = modifierId
        return persist(exceptionalNote)
    }

    /**
     * Lists exceptional notes
     *
     * @param customerOrder list only notes that belong to this order
     *
     * @return notes
     */
    fun list (customerOrder: CustomerOrder): List<ExceptionalNote> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(ExceptionalNote::class.java)
        val root = criteria.from(ExceptionalNote::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()
        restrictions.add(criteriaBuilder.equal(root.get(ExceptionalNote_.customerOrder), customerOrder))

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}
