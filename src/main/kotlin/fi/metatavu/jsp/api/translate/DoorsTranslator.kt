package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.Door

/**
 * A translator class for doors
 */
class DoorsTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.Door, Door>() {

    /**
     * Translated JPA doors into REST doors
     *
     * @param entity a product to translate
     *
     * @return translated doors
     */
    override fun translate(entity: fi.metatavu.jsp.persistence.model.Door): Door {
        val door = Door()
        door.id = entity.id
        door.doorColor = entity.doorColor
        door.glassColor = entity.glasscolor
        door.isGlassDoor = entity.isglassdoor
        door.modelName = entity.modelName

        return door
    }

}