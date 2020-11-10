package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.CounterFrame

/**
 * A translator class for counter frames
 */
class CounterFrameTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.CounterFrame, CounterFrame>() {
    /**
     * Translates a JPA counter frame into a REST counter frame
     *
     * @param entity an entity to translate
     *
     * @return a translated entity
     */
    override fun translate(entity: fi.metatavu.jsp.persistence.model.CounterFrame): CounterFrame {
        val counterFrame = CounterFrame()

        counterFrame.id = entity.id
        counterFrame.additionalInformation = entity.additionalInformation
        counterFrame.plinth = entity.plinth
        counterFrame.extraSide = entity.extraSide
        counterFrame.cornerStripe = entity.cornerStripe
        counterFrame.color = entity.color

        return counterFrame
    }
}