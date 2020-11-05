package fi.metatavu.jsp.persistence.dao

import fi.metatavu.jsp.api.spec.model.GenericProductType
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.GenericProduct
import fi.metatavu.jsp.persistence.model.GenericProduct_
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.criteria.Predicate

/**
 * A DAO-class for generic products
 */
@ApplicationScoped
class GenericProductDAO: AbstractDAO<GenericProduct>() {
    /**
     * Saves a product to database
     *
     * @param id An UUID for identification
     * @param name a product name
     * @param type a product type
     * @param supplier a product supplier
     * @param customerOrder the order to which this product is related to
     * @param creatorId id of the user who is creating this product
     *
     * @return a created product
     */
    fun create (id: UUID, name: String, type: GenericProductType, supplier: String, customerOrder: CustomerOrder, creatorId: UUID): GenericProduct {
        val product = GenericProduct()
        product.id = id
        product.customerOrder = customerOrder
        product.productName = name
        product.productType = type
        product.supplier = supplier
        product.creatorId = creatorId
        product.lastModifierId = creatorId
        return persist(product)
    }

    /**
     * Updates product name
     *
     * @param product a product to update
     * @param name a new name
     * @param lastModifierId id of the user who is editing this product
     *
     * @return an updated product
     */
    fun updateName (product: GenericProduct, name: String, lastModifierId: UUID): GenericProduct {
        product.productName = name
        product.lastModifierId = lastModifierId
        return persist(product)
    }

    /**
     * Updates supplier
     *
     * @param product a product to update
     * @param supplier a new supplier
     * @param lastModifierId id of the user who is editing this product
     *
     * @return an updated product
     */
    fun updateSupplier (product: GenericProduct, supplier: String, lastModifierId: UUID): GenericProduct {
        product.supplier = supplier
        product.lastModifierId = lastModifierId
        return persist(product)
    }

    /**
     * Lists products
     *
     * @param type list only products belonging to this type
     * @param customerOrder list only products belonging to this order
     *
     * @return products
     */
    fun list (type: GenericProductType?, customerOrder: CustomerOrder?): List<GenericProduct> {
        val entityManager = getEntityManager()
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteria = criteriaBuilder.createQuery(GenericProduct::class.java)
        val root = criteria.from(GenericProduct::class.java)

        criteria.select(root)
        val restrictions = ArrayList<Predicate>()

        if (type != null) {
            restrictions.add(criteriaBuilder.equal(root.get(GenericProduct_.productType), type))
        }

        if (customerOrder != null) {
            restrictions.add(criteriaBuilder.equal(root.get(GenericProduct_.customerOrder), customerOrder))
        }

        criteria.where(criteriaBuilder.and(*restrictions.toTypedArray()));

        val query = entityManager.createQuery(criteria)
        return query.resultList
    }
}