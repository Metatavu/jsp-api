package fi.metatavu.jsp.tags

import fi.metatavu.jsp.persistence.dao.TagDAO
import fi.metatavu.jsp.persistence.model.Tag
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * Controller for tags
 */
@ApplicationScoped
class TagController() {

    @Inject
    private lateinit var tagDAO: TagDAO

    /**
     * Creates new tag
     *
     * @param name tag name
     * @param creatorId creating user id
     * @return created tag
     */
    fun createTag(name: String, creatorId: UUID): Tag {
        return tagDAO.create(
                UUID.randomUUID(),
                name = name,
                creatorId = creatorId,
                lastModifierId = creatorId
        )
    }

    /**
     * Finds tag by id
     *
     * @param id tag id to search with
     * @return found tag or null if not found
     */
    fun findTagById(id: UUID): Tag? {
        return tagDAO.findById(id)
    }

    /**
     * Lists all tags
     *
     * @returns list of tags
     */
    fun listTags(): List<Tag> {
        return tagDAO.listAll()
    }

    /**
     * Updates a tag
     *
     * @param tag tag to be updated
     * @param name tag name
     * @param modifierId modifying user id
     * @return updated tag
     */
    fun updateTag(tag: Tag, name: String, modifierId: UUID): Tag {
        return tagDAO.updateName(tag, name, modifierId)
    }

    /**
     * Deletes a tag
     *
     * @param tag tag to be deleted
     */
    fun deleteTag(tag: Tag) {
        return tagDAO.delete(tag)
    }
}