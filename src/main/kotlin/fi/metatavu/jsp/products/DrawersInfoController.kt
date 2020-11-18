package fi.metatavu.jsp.products

import fi.metatavu.jsp.persistence.dao.DrawersInfoDao
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.DrawersInfo
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class DrawersInfoController {

    @Inject
    private lateinit var drawersInfoDao: DrawersInfoDao

    /**
     * Saves drawers to database
     *
     * @param trashBins trash bins
     * @param cutleryCompartments cutlery compartments
     * @param isMarkedInImages is drawer marked in images
     * @param additionalInformation additional information
     * @param customerOrder an order to which this handle belongs to
     * @param creatorId id of the user who is creating this handle
     *
     * @return created drawers
     */
    fun create(
        trashBins: String,
        cutleryCompartments: String,
        isMarkedInImages: Boolean,
        additionalInformation: String,
        customerOrder: CustomerOrder,
        creatorId: UUID
    ): DrawersInfo {
        return drawersInfoDao.create(UUID.randomUUID(), trashBins, cutleryCompartments, isMarkedInImages, additionalInformation, customerOrder, creatorId)
    }

    /**
     * Updates drawers
     *
     * @param drawersInfo a drawers to update
     * @param trashBins new trash bins for drawers
     * @param cutleryCompartments new cutlery compartments for drawers
     * @param isMarkedInImages new value for is marked in images
     * @param additionalInformation additional information
     * @param lastModifierId id of the user who is modifying this drawers
     *
     * @return updated drawers
     */
    fun update(
        drawersInfo: DrawersInfo,
        trashBins: String,
        cutleryCompartments: String,
        isMarkedInImages: Boolean,
        additionalInformation: String,
        lastModifierId: UUID
    ): DrawersInfo {

        drawersInfoDao.UpdateCutleryCompartments(drawersInfo, cutleryCompartments, lastModifierId)
        drawersInfoDao.UpdateIsMarkedInImages(drawersInfo, isMarkedInImages, lastModifierId)
        drawersInfoDao.UpdateTrashBins(drawersInfo, trashBins, lastModifierId)
        drawersInfoDao.UpdateAdditionalInformation(drawersInfo, additionalInformation, lastModifierId)

        return drawersInfo
    }

    /**
     * Lists drawers
     *
     * @param customerOrder list only drawers belonging to this order
     *
     * @return drawers
     */
    fun list(customerOrder: CustomerOrder?): List<DrawersInfo> {
        return drawersInfoDao.list(customerOrder)
    }

    /**
     * Finds drawers
     *
     * @param drawersId the id of drawers to find
     *
     * @return found drawers or null if not found
     */
    fun find (drawersId: UUID): DrawersInfo? {
        return drawersInfoDao.findById(drawersId)
    }

    /**
     * Deletes drawers
     *
     * @param drawersInfo drawers to delete
     */
    fun delete (drawersInfo: DrawersInfo) {
        drawersInfoDao.delete(drawersInfo)
    }
}
