package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.Installation
import javax.enterprise.context.ApplicationScoped

/**
 * A translator for installation informations
 */
@ApplicationScoped
class InstallationTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.Installation, Installation>() {

    /**
     * Translates JPA handles into REST handles
     *
     * @param entity an entity to translate
     *
     * @return a translated entity
     */
    override fun translate(entity: fi.metatavu.jsp.persistence.model.Installation): Installation {
        val installation = Installation()
        installation.id = entity.id
        installation.isCustomerInstallation = entity.isCustomerInstallation
        installation.additionalInformation = entity.additionalInformation

        return installation
    }
}