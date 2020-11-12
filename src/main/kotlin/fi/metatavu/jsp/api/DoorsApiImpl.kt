package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.DoorsApi
import fi.metatavu.jsp.api.spec.model.Door
import fi.metatavu.jsp.api.translate.DoorsTranslator
import fi.metatavu.jsp.products.DoorsController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response
import kotlin.collections.ArrayList

/**
 * Endpoints for generic products
 */
@RequestScoped
@Stateful
class DoorsApiImpl: DoorsApi, AbstractApi() {

    @Inject
    private lateinit var doorsController: DoorsController

    @Inject
    private lateinit var doorsTranslator: DoorsTranslator

    override fun findDoor(doorId: UUID): Response {
        val foundDoor = doorsController.find(doorId) ?: return createNotFound("The door with id $doorId not found!")
        return createOk(doorsTranslator.translate(foundDoor))
    }

    override fun listDoors(): Response {
        val doors = doorsController.list(null)
        val translatedDoors = doors.map(doorsTranslator::translate)
        return createOk(translatedDoors)
    }
}
