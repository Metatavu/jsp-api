package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.Image
import fi.metatavu.jsp.persistence.model.Tag
import java.util.*
import javax.enterprise.context.ApplicationScoped

/**
 * DAO class for Tag
 *
 * @author Antti Leinonen
 */
@ApplicationScoped
class ImageDAO() : AbstractDAO<Image>() {

    /**
     * Creates a new Tag
     *
     * @param id id
     * @param name device manufacturer
     * @param creatorId creator's id
     * @param lastModifierId last modifier's id
     * @return created Tag
     */
    fun create(id: UUID, url: String, name: String?, description: String?, creatorId: UUID, lastModifierId: UUID): Image {
        val image = Image()
        image.id = id
        image.url = url
        image.name = name
        image.description = description
        image.creatorId = creatorId
        image.lastModifierId = lastModifierId
        return persist(image)
    }

    /**
     * Updates image name
     *
     * @param image image to update
     * @param name name to set for image
     * @param lastModifierId last modifier's id
     * @return updated Tag
     */
    fun updateName(image: Image, name: String, lastModifierId: UUID): Image {
        image.lastModifierId = lastModifierId
        image.name = name
        return persist(image)
    }

    /**
     * Updates image name
     *
     * @param image image to update
     * @param name name to set for image
     * @param lastModifierId last modifier's id
     * @return updated Tag
     */
    fun updateUrl(image: Image, url: String, lastModifierId: UUID): Image {
        image.lastModifierId = lastModifierId
        image.url = url
        return persist(image)
    }

    /**
     * Updates image description
     *
     * @param image image to update
     * @param description description to set for image
     * @param lastModifierId last modifier's id
     * @return updated Tag
     */
    fun updateDescription(image: Image, description: String, lastModifierId: UUID): Image {
        image.lastModifierId = lastModifierId
        image.description = description
        return persist(image)
    }
}