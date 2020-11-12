package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.CounterFramesApi
import fi.metatavu.jsp.api.translate.CounterFrameTranslator
import fi.metatavu.jsp.products.CounterFramesController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * Endpoints for counter frames
 */
@RequestScoped
@Stateful
class CounterFramesApiImpl: CounterFramesApi, AbstractApi() {

    @Inject
    private lateinit var counterFramesController: CounterFramesController

    @Inject
    private lateinit var counterFrameTranslator: CounterFrameTranslator

    override fun findCounterFrame(counterFrameId: UUID): Response {
        val foundCounterFrame = counterFramesController.find(counterFrameId) ?: return createNotFound("A counter frame with id $counterFrameId not found!")
        return createOk(counterFrameTranslator.translate(foundCounterFrame))
    }

    override fun listCounterFrames(): Response {
        val counterFrames = counterFramesController.list(null)
        return createOk(counterFrames.map(counterFrameTranslator::translate))
    }
}
