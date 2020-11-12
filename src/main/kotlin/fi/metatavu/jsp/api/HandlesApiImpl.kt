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
        return createOk(handleTranslator.translate(foundHandle))
    }

    override fun listHandles(): Response {
        val handles = handlesController.list(null)

        return createOk(handles.map(handleTranslator::translate))
    }
}
