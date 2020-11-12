package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.HandlesApi
import fi.metatavu.jsp.api.translate.HandleTranslator
import fi.metatavu.jsp.products.HandlesController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * Endpoints for handles
 */
@RequestScoped
@Stateful
class HandlesApiImpl: HandlesApi, AbstractApi() {

    @Inject
    private lateinit var handleTranslator: HandleTranslator

    @Inject
    private lateinit var handlesController: HandlesController

    override fun findHandle(handleId: UUID): Response {
        val foundHandle = handlesController.find(handleId) ?: return createNotFound("A handle with id $handleId not found!")
        val translatedHandle = handleTranslator.translate(foundHandle)

        return createOk(translatedHandle)
    }

    override fun listHandles(): Response {
        val handles = handlesController.list(null)
        val translatedHandles = handles.map(handleTranslator::translate)

        return createOk(translatedHandles)
    }
}
