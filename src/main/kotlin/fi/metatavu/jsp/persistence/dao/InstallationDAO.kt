package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.Installation
import fi.metatavu.jsp.persistence.model.Installation_
import fi.metatavu.jsp.persistence.model.CustomerOrder
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate

@ApplicationScoped
class InstallationDAO: AbstractDAO<Installation>() {

    /**
     * Saves installation information to a database
     *
     * @param id An UUID for identification
     * @param isCustomerInstallation is it installed by customer
     * @param additionalInformation additional information
     * @param customerOrder an order to which this installation information belongs to
     * @param creatorId id of the user who is creating this information
     *
     * @return created installation information
     */
    fun create(
        id: UUID,
        isCustomerInstallation: Boolean,
        additionalInformation: String,
        customerOrder: CustomerOrder,
        creatorId: UUID
    ): Installation {

        val installation = Installation()
        installation.id = id
        installation.isCustomerInstallation = isCustomerInstallation
        installation.additionalInformation = additionalInformation
        installation.creatorId = creatorId
        installation.lastModifierId = creatorId
        installation.customerOrder = customerOrder

        return persist(installation)
    }

    /**
     * updates value to isCustomerInstallation
     *
     * @param installation installation to update
     * @param isCustomerInstallation new value to isCustomerInstallation
     * @param lastModifierId id of the user who is modifying this installation information
     *
     * @return updated installation information
     */
    fun updateIsCustomerInstallation(installation: Installation, isCustomerInstallation: Boolean, lastModifierId: UUID): Installation {
        installation.isCustomerInstallation = isCustomerInstallation
        installation.lastModifierId = lastModifierId
        return persist(installation)
    }

    /**
     * updates additional information
     *
     * @param installation installation to update
     * @param additionalInformation updated additional information
     * @param lastModifierId id of the user who is modifying this installation information
     *
     * @return updated installation information
     */
    fun updateAdditionalInformation(installation: Installation, additionalInformation: String, lastModifierId: UUID): Installation {
        installation.additionalInformation = additionalInformation
        installation.lastModifierId = lastModifierId
        return persist(installation)
    }

    /**
     * Lists handles
     *
     * @param customerOrder list only handles belonging to this order
     *
     * @return handles
     */
    fun list(customerOrder: CustomerOrder?): List<Installation> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(Installation::class.java)
        val root = criteria.from(Installation::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()

        if (customerOrder != null) {
            restrictions.add(criteriaBuilder.equal(root.get(Installation_.customerOrder), customerOrder))
        }

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}
