package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.TagsApi
import fi.metatavu.jsp.api.spec.model.Tag
import fi.metatavu.jsp.api.translate.TagTranslator
import fi.metatavu.jsp.tags.TagController
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * Tags API REST endpoints
 *
 * @author Antti Leinonen
 */
@RequestScoped
@Stateful
@Suppress("unused")
class TagsApiImpl: TagsApi, AbstractApi() {

    @Inject
    private lateinit var tagController: TagController

    @Inject
    private lateinit var tagTranslator: TagTranslator

    /* Tags */

    override fun createTag(payload: Tag?): Response {
        payload ?: return createBadRequest("Missing request body")
        val userId = loggerUserId ?: return createUnauthorized(UNAUTHORIZED)
        val name = payload.name
        val tag = tagController.createTag(name, userId)
        return createOk(tagTranslator.translate(tag))
    }

    override fun listTags(): Response {
        val tags = tagController.listTags()
        loggerUserId ?: return createUnauthorized(UNAUTHORIZED)
        return createOk(tags.map (tagTranslator::translate))
    }

    override fun updateTag(payload: Tag?): Response {
        payload ?: return createBadRequest("Missing request body")
        val userId = loggerUserId ?: return createUnauthorized(UNAUTHORIZED)
        val tagToUpdate = tagController.findTagById(payload.id) ?: return createNotFound("Tag not found")

        return createOk(tagController.updateTag(tagToUpdate, payload.name, userId))
    }

    override fun deleteTag(payload: Tag?): Response {
        payload ?: return createBadRequest("Missing request body")
        loggerUserId ?: return createUnauthorized(UNAUTHORIZED)
        val tagToDelete = tagController.findTagById(payload.id) ?: return createNotFound("Tag not found with id: ${payload.id}")

        tagController.deleteTag(tagToDelete)
        return createNoContent()
    }
}