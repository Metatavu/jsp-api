package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.GenericProductsApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.infrastructure.ClientException
import fi.metatavu.jsp.api.client.models.GenericProduct
import fi.metatavu.jsp.api.test.functional.settings.TestSettings
import org.junit.Assert.assertEquals
import java.lang.Exception
import java.util.*

class GenericProductTestBuilderResource (testBuilder: AbstractTestBuilder<ApiClient?>?, private val accessTokenProvider: AccessTokenProvider?, apiClient: ApiClient): ApiTestBuilderResource<GenericProduct, ApiClient>(testBuilder, apiClient) {
    override fun clean(t: GenericProduct?) {
        TODO("Will not be implemented")
    }

    override fun getApi(): GenericProductsApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return GenericProductsApi(TestSettings.apiBasePath)
    }

    /**
     * Sends a request to find a product
     *
     * @param productId the of a product to find
     *
     * @return found product
     */
    fun find (productId: UUID): GenericProduct {
        return api.findGenericProduct(productId)
    }

    /**
     * Tests that find request fails correctly
     */
    fun assertFindFailStatus (productId: UUID) {
        try {
            api.findGenericProduct(productId)
            throw Exception("Should have failed with status 404")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(404, exception)
        }
    }

    /**
     * Sends a request to list products
     *
     * @param productType list only products belonging to this type
     *
     * @return an array of products
     */
    fun list(productType: String?): Array<GenericProduct> {
        return api.listGenericProducts(productType)
    }

    /**
     * Tests that list request fails correctly
     */
    fun assertListFailStatus () {
        try {
            api.listGenericProducts("FAILURE")
            throw Exception("Should have failed with status 400")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(400, exception)
        }
    }

    fun assertGenericProductsEqual (genericProduct1: GenericProduct, genericProduct2: GenericProduct) {
        assertEquals(genericProduct1.name, genericProduct2.name)
        assertEquals(genericProduct1.supplier, genericProduct2.supplier)
        assertEquals(genericProduct1.type, genericProduct2.type)
    }
}