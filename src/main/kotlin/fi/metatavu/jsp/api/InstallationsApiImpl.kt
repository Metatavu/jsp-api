package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.InstallationsApi
import fi.metatavu.jsp.api.translate.InstallationTranslator
import fi.metatavu.jsp.products.InstallationsController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * Endpoints for installation informations
 */
@RequestScoped
@Stateful
class InstallationsApiImpl: InstallationsApi, AbstractApi() {

    @Inject
    lateinit var installationTranslator: InstallationTranslator

    @Inject
    lateinit var installationsController: InstallationsController

    override fun findInstallation(installationId: UUID): Response {
        val foundInstallation = installationsController.find(installationId) ?: return createNotFound("An installation information with id $installationId not found")
        return createOk(installationTranslator.translate(foundInstallation))
    }

    override fun listInstallations(): Response {
        val installations = installationsController.list(null)

        return createOk(installations.map(installationTranslator::translate))
    }
}
