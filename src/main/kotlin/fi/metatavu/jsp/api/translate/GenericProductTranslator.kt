package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.GenericProduct

/**
 * A translator class for generic products
 */
class GenericProductTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.GenericProduct, GenericProduct>() {

    /**
     * Translated JPA products into REST products
     *
     * @param entity a product to translate
     *
     * @return translated products
     */
    override fun translate(entity: fi.metatavu.jsp.persistence.model.GenericProduct): GenericProduct {
        val genericProduct = GenericProduct()
        genericProduct.id = entity.id
        genericProduct.supplier = entity.supplier
        genericProduct.type = entity.productType
        genericProduct.name = entity.productName

        return genericProduct
    }

}