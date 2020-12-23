package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.*

import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate
import kotlin.collections.ArrayList

/**
 * A DAO-class for Files
 */
@ApplicationScoped
class FileDAO: AbstractDAO<File>() {

    /**
     * Saves a new file to the database
     *
     * @param id An UUID for identification
     * @param name file name
     * @param url file url
     * @param customerFile customerFile
     * @param customerOrder order that this file is related to
     * @param creatorId id of the user who is creating this file
     */
    fun create (id: UUID, name: String, url: String, customerFile: Boolean, customerOrder: CustomerOrder, creatorId: UUID): File {
        val file = File()
        file.id = id
        file.customerOrder = customerOrder
        file.name = name
        file.url = url
        file.customerFile = customerFile
        file.creatorId = creatorId
        file.lastModifierId = creatorId
        return persist(file)
    }

    /**
     * Updates File
     *
     * @param file file to update
     * @param modifierId id of user who made the modification
     * @param name new new
     * @param url new url
     *
     * @return an updated file
     */
    fun updateFile (file: File, modifierId: UUID, name: String, url: String): File {
        file.name = name
        file.url = url
        file.lastModifierId = modifierId
        return persist(file)
    }

    /**
     * Lists files
     *
     * @param customerOrder list only files that belong to this order
     * @param customerFile list customer files
     *
     * @return list of files
     */
    fun list (customerOrder: CustomerOrder?, customerFile: Boolean?): List<File> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(File::class.java)
        val root = criteria.from(File::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()

        if (customerOrder != null) {
            restrictions.add(criteriaBuilder.equal(root.get(File_.customerOrder), customerOrder))
        }

        if (customerFile != null){
            restrictions.add(criteriaBuilder.equal(root.get(File_.customerFile), customerFile))
        }

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}