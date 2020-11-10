package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.Handle
import javax.enterprise.context.ApplicationScoped

/**
 * A translator for handles
 */
@ApplicationScoped
class HandleTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.Handle, Handle>() {

    /**
     * Translates JPA handles into REST handles
     *
     * @param entity an entity to translate
     *
     * @return a translated entity
     */
    override fun translate(entity: fi.metatavu.jsp.persistence.model.Handle): Handle {
        val handle = Handle()
        handle.id = entity.id
        handle.color = entity.color
        handle.doorModelName = entity.doorModelName
        handle.markedInImages = entity.isMarkedInImages

        return handle
    }
}
