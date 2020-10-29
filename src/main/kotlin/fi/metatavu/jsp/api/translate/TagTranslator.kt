package fi.metatavu.jsp.api.translate

import javax.enterprise.context.ApplicationScoped

/**
 * Translator for translating JPA tag entities into REST resources
 */
@ApplicationScoped
class TagTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.Tag, fi.metatavu.jsp.api.spec.model.Tag>() {

    override fun translate(entity: fi.metatavu.jsp.persistence.model.Tag): fi.metatavu.jsp.api.spec.model.Tag {
        val result = fi.metatavu.jsp.api.spec.model.Tag()
        result.id = entity.id
        result.name = entity.name
        result.creatorId = entity.creatorId
        result.lastModifierId = entity.lastModifierId
        result.createdAt = entity.createdAt
        result.modifiedAt = entity.modifiedAt

        return result
    }
}
