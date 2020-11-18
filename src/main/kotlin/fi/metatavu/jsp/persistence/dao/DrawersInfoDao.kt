package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.DrawersInfo
import fi.metatavu.jsp.persistence.model.DrawersInfo_
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate

@ApplicationScoped
class DrawersInfoDao: AbstractDAO<DrawersInfo>() {

    /**
     * Saves drawers to database
     *
     * @param id An UUID for identification
     * @param trashBins trash bins
     * @param cutleryCompartments cutlery compartments
     * @param isMarkedInImages is drawer marked in images
     * @param additionalInformation
     * @param customerOrder an order to which this handle belongs to
     * @param creatorId id of the user who is creating this handle
     *
     * @return created drawers
     */
    fun create(
        id: UUID,
        trashBins: String,
        cutleryCompartments: String,
        isMarkedInImages: Boolean,
        additionalInformation: String,
        customerOrder: CustomerOrder,
        creatorId: UUID
    ): DrawersInfo {

        val drawersInfo = DrawersInfo()
        drawersInfo.id = id
        drawersInfo.trashBins = trashBins
        drawersInfo.cutleryCompartments = cutleryCompartments
        drawersInfo.isMarkedInImages = isMarkedInImages
        drawersInfo.additionalInformation = additionalInformation
        drawersInfo.creatorId = creatorId
        drawersInfo.lastModifierId = creatorId
        drawersInfo.customerOrder = customerOrder

        return persist(drawersInfo)
    }

    /**
     * Updates trash bins
     *
     * @param drawersInfo a drawers to update
     * @param trashBins new trash bins for drawer
     * @param lastModifierId id of the user who is modifying this drawers
     *
     * @return updated drawer
     */
    fun UpdateTrashBins(drawersInfo: DrawersInfo, trashBins: String, lastModifierId: UUID): DrawersInfo {
        drawersInfo.trashBins = trashBins
        drawersInfo.lastModifierId

        return persist(drawersInfo)
    }

    /**
     * Updates drawers cutlery compartments
     *
     * @param drawersInfo drawers to update
     * @param cutleryCompartments drawers cutlery compartments
     * @param lastModifierId id of the user who is modifying this drawers
     *
     * @return updated drawer
     */
    fun UpdateCutleryCompartments(drawersInfo: DrawersInfo, cutleryCompartments: String, lastModifierId: UUID): DrawersInfo {
        drawersInfo.cutleryCompartments = cutleryCompartments
        drawersInfo.lastModifierId = lastModifierId

        return persist(drawersInfo)
    }

    /**
     * Updates a a value for isMarkedInImages
     *
     * @param drawersinfo drawers to update
     * @param isMarkedInImages is marked in images
     * @param lastModifierId id of the user who is modifying this drawers
     *
     * @return updated drawer
     */
    fun UpdateIsMarkedInImages(drawersinfo: DrawersInfo, isMarkedInImages: Boolean, lastModifierId: UUID): DrawersInfo {
        drawersinfo.isMarkedInImages = isMarkedInImages
        drawersinfo.lastModifierId = lastModifierId

        return persist(drawersinfo)
    }

    /**
     * Updates additional information
     *
     * @param drawersInfo drawers to update
     * @param additionalInformation is marked in images
     * @param lastModifierId id of the user who is modifying this drawers
     *
     * @return updated drawer
     */
    fun UpdateAdditionalInformation(drawersInfo: DrawersInfo, additionalInformation: String, lastModifierId: UUID): DrawersInfo {
        drawersInfo.additionalInformation = additionalInformation
        drawersInfo.lastModifierId

        return persist(drawersInfo)
    }

    /**
     * Lists drawers
     *
     * @param customerOrder list only drawers belonging to this order
     *
     * @return drawers
     */
    fun list(customerOrder: CustomerOrder?): List<DrawersInfo> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(DrawersInfo::class.java)
        val root = criteria.from(DrawersInfo::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()

        if (customerOrder != null) {
            restrictions.add(criteriaBuilder.equal(root.get(DrawersInfo_.customerOrder), customerOrder))
        }

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}
