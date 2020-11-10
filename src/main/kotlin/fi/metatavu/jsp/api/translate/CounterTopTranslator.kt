package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.CounterTop

/**
 * A translator for counter tops
 */
class CounterTopTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.CounterTop, CounterTop>() {
    /**
     * Translates JPA counter tops into REST counter tops
     *
     * @param entity an entity to translate
     *
     * @return a translated entity
     */
    override fun translate(entity: fi.metatavu.jsp.persistence.model.CounterTop): CounterTop {
        val counterTop = CounterTop()
        counterTop.id = entity.id
        counterTop.modelName = entity.modelName
        counterTop.type = entity.counterTopType
        counterTop.thickness = entity.thickness

        return counterTop
    }
}