package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.DrawersApi
import fi.metatavu.jsp.api.translate.DrawersInfoTranslator
import fi.metatavu.jsp.products.DrawersInfoController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * Endpoints for drawers
 */
@RequestScoped
@Stateful
class DrawersApiImpl: DrawersApi, AbstractApi() {

    @Inject
    private lateinit var drawersInfoTranslator: DrawersInfoTranslator

    @Inject
    private lateinit var drawersInfoController: DrawersInfoController

    override fun findDrawersInfo(drawersInfoId: UUID): Response {
        val foundDrawers = drawersInfoController.find(drawersInfoId) ?: return createNotFound("Drawers with id $drawersInfoId not found")
        return createOk(drawersInfoTranslator.translate(foundDrawers))
    }

    override fun listDrawerInfos(): Response {
        val drawers = drawersInfoController.list(null)

        return createOk(drawers.map(drawersInfoTranslator::translate))
    }
}