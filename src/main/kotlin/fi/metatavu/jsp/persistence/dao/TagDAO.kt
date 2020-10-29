package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.Tag
import java.util.*
import javax.enterprise.context.ApplicationScoped

/**
 * DAO class for Tag
 *
 * @author Antti Leinonen
 */
@ApplicationScoped
class TagDAO() : AbstractDAO<Tag>() {

    /**
     * Creates a new Tag
     *
     * @param id id
     * @param name tag name
     * @param creatorId creator's id
     * @param lastModifierId last modifier's id
     * @return created Tag
     */
    fun create(id: UUID, name: String?, creatorId: UUID, lastModifierId: UUID): Tag {
        val tag = Tag()
        tag.id = id
        tag.name = name
        tag.creatorId = creatorId
        tag.lastModifierId = lastModifierId
        return persist(tag)
    }

    /**
     * Updates tag name
     *
     * @param tag tag to update
     * @param name name to set for tag
     * @param lastModifierId last modifier's id
     * @return updated Tag
     */
    fun updateName(tag: Tag, name: String, lastModifierId: UUID): Tag {
        tag.lastModifierId = lastModifierId
        tag.name = name
        return persist(tag)
    }

    /**
     * Deletes Tag
     *
     * @param tag tag to delete
     * @return deleted Tag
     */
    fun deleteTag(tag: Tag, name: String, lastModifierId: UUID): Tag {
        tag.lastModifierId = lastModifierId
        tag.name = name
        return persist(tag)
    }
}