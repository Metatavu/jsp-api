package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.DrawersInfo
import javax.enterprise.context.ApplicationScoped

/**
 * A translator for drawers
 */
@ApplicationScoped
class DrawersInfoTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.DrawersInfo, DrawersInfo>() {

    override fun translate(entity: fi.metatavu.jsp.persistence.model.DrawersInfo): DrawersInfo {
        val drawersInfo = DrawersInfo()
        drawersInfo.id = entity.id
        drawersInfo.cutleryCompartments = entity.cutleryCompartments
        drawersInfo.trashbins= entity.trashBins
        drawersInfo.markedInImages = entity.isMarkedInImages
        drawersInfo.additionalInformation = entity.additionalInformation

        return drawersInfo
    }
}