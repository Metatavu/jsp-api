package fi.metatavu.jsp.api

import fi.metatavu.jsp.api.spec.GenericProductsApi
import fi.metatavu.jsp.persistence.model.GenericProduct
import fi.metatavu.jsp.api.spec.model.GenericProductType
import fi.metatavu.jsp.api.translate.GenericProductTranslator
import fi.metatavu.jsp.products.GenericProductsController
import java.util.*
import javax.ejb.Stateful
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * Endpoints for generic products
 */
@RequestScoped
@Stateful
class GenericProductsApiImpl: GenericProductsApi, AbstractApi() {
    @Inject
    private lateinit var genericProductsController: GenericProductsController

    @Inject
    private lateinit var genericProductTranslator: GenericProductTranslator

    override fun findGenericProduct(productId: UUID): Response {
        val foundProduct = genericProductsController.find(productId) ?: return createNotFound("The generic product with id $productId not found!")
        val translatedProduct = genericProductTranslator.translate(foundProduct)
        return createOk(translatedProduct)
    }

    override fun listGenericProducts(productType: String?): Response {
        val products = ArrayList<GenericProduct>()
        if (productType == null) {
            products.addAll(genericProductsController.list(null, null))
        } else if (productType != "ELECTRIC" && productType != "DOMESTIC_APPLIANCE" && productType != "ELECTRIC" && productType != "SINK" && productType != "INTERMEDIATE_SPACE") {
            return createBadRequest("Unrecognized type $productType")
        } else  {
            products.addAll(genericProductsController.list(GenericProductType.fromValue(productType), null))
        }

        val translatedProducts = products.map(genericProductTranslator::translate)
        return createOk(translatedProducts)
    }
}