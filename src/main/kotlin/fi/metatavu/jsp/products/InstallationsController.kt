package fi.metatavu.jsp.products

import fi.metatavu.jsp.persistence.model.Installation
import fi.metatavu.jsp.persistence.dao.InstallationDAO
import fi.metatavu.jsp.persistence.model.CustomerOrder
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * A controller fo installation information
 */
@ApplicationScoped
class InstallationsController {

    @Inject
    private lateinit var installationDAO: InstallationDAO

    /**
     * Saves installation information to a database
     *
     * @param isCustomerInstallation is it installed by customer
     * @param additionalInformation additional information
     * @param customerOrder an order to which this installation information belongs to
     * @param creatorId id of the user who is creating this information
     *
     * @return created installation information
     */
    fun create(
        isCustomerInstallation: Boolean,
        additionalInformation: String,
        customerOrder: CustomerOrder,
        creatorId: UUID
    ): Installation {

        return installationDAO.create(UUID.randomUUID(), isCustomerInstallation, additionalInformation, customerOrder, creatorId)
    }

    /**
     * updates installation information
     *
     * @param installation installation to update
     * @param isCustomerInstallation new value to isCustomerInstallation
     * @param additionalInformation additional information
     * @param lastModifierId id of the user who is modifying this installation information
     *
     * @return updated installation information
     */
    fun update(
        installation: Installation,
        isCustomerInstallation: Boolean,
        additionalInformation: String,
        lastModifierId: UUID
    ): Installation {

        installationDAO.updateAdditionalInformation(installation, additionalInformation, lastModifierId)
        installationDAO.updateIsCustomerInstallation(installation, isCustomerInstallation, lastModifierId)

        return installation
    }

    /**
    * Lists installations
    *
    * @param customerOrder list only installations belonging to this order
    *
    * @return installations
    */
    fun list (customerOrder: CustomerOrder?): List<Installation> {
        return installationDAO.list(customerOrder)
    }

    /**
     * Finds an installation information
     *
     * @param installationId the id of an installation to find
     *
     * @return found installation or null if not found
     */
    fun find (installationId: UUID): Installation? {
        return installationDAO.findById(installationId)
    }

    /**
     * Deletes an installation information
     *
     * @param installation a handle to delete
     */
    fun delete (installation: Installation) {
        installationDAO.delete(installation)
    }
}