package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.CounterTopsApi
import fi.metatavu.jsp.api.translate.CounterTopTranslator
import fi.metatavu.jsp.products.CounterTopsController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * Endpoints for counter tops
 */
@RequestScoped
@Stateful
class CounterTopsApiImpl: CounterTopsApi, AbstractApi() {
    @Inject
    private lateinit var counterTopsController: CounterTopsController

    @Inject
    private lateinit var counterTopTranslator: CounterTopTranslator

    override fun findCounterTop(counterTopId: UUID): Response {
        val foundCounterTop = counterTopsController.find(counterTopId) ?: return createNotFound("A counter top with id $counterTopId not found!")
        val translatedCounterTop = counterTopTranslator.translate(foundCounterTop)

        return createOk(translatedCounterTop)
    }

    override fun listCounterTops(): Response {
        val counterTops = counterTopsController.list(null)
        val translatedCounterTops = counterTops.map(counterTopTranslator::translate)

        return createOk(translatedCounterTops)
    }
}