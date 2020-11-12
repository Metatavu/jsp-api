package fi.metatavu.jsp.products

import fi.metatavu.jsp.api.spec.model.GenericProductType
import fi.metatavu.jsp.persistence.dao.GenericProductDAO
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.GenericProduct
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class GenericProductsController {
    @Inject
    private lateinit var genericProductDAO: GenericProductDAO

    /**
     * Saves a product to database
     *
     * @param name a product name
     * @param type a product type
     * @param supplier a product supplier
     * @param customerOrder the order to which this product is related to
     * @param creatorId id of the user who is creating this product
     *
     * @return a created product
     */
    fun create (name: String, type: GenericProductType, supplier: String, customerOrder: CustomerOrder, creatorId: UUID): GenericProduct {
        return genericProductDAO.create(UUID.randomUUID(), name, type, supplier, customerOrder, creatorId)
    }

    /**
     * Updates a product
     *
     * @param product a product to update
     * @param name a new name
     * @param supplier a new supplier
     * @param lastModifierId id of the user who is modifying this product
     *
     * @return an updated product
     */
    fun update (product: GenericProduct, name: String, supplier: String, lastModifierId: UUID): GenericProduct {
        genericProductDAO.updateName(product, name, lastModifierId)
        genericProductDAO.updateSupplier(product, supplier, lastModifierId)
        return product
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
        return genericProductDAO.list(type, customerOrder)
    }

    /**
     * Finds a product
     *
     * @param productId the id of a product to find
     *
     * @return found product or null if not found
     */
    fun find (productId: UUID): GenericProduct? {
        return genericProductDAO.findById(productId)
    }

    /**
     * Deletes a product
     *
     * @param genericProduct a product to delete
     */
    fun delete (genericProduct: GenericProduct) {
        genericProductDAO.delete(genericProduct)
    }
}
