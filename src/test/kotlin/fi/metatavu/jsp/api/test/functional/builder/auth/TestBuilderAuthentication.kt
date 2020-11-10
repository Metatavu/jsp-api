package fi.metatavu.jsp.api.test.functional.builder.auth

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jaxrs.test.functional.builder.auth.AuthorizedTestBuilderAuthentication
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.test.functional.builder.impl.CounterFrameTestBuilderResource
import fi.metatavu.jsp.api.test.functional.builder.impl.GenericProductTestBuilderResource
import fi.metatavu.jsp.api.test.functional.builder.impl.OrderTestBuilderResource
import fi.metatavu.jsp.api.test.functional.settings.TestSettings

/**
 * Test builder authentication
 *
 * Constructor
 *
 * @param testBuilder test builder instance
 * @param accessTokenProvider access token provider
 */
class TestBuilderAuthentication(testBuilder: AbstractTestBuilder<ApiClient>, accessTokenProvider: AccessTokenProvider) : AuthorizedTestBuilderAuthentication<ApiClient>(testBuilder, accessTokenProvider) {
    private var accessTokenProvider: AccessTokenProvider? = accessTokenProvider
    private var orders: OrderTestBuilderResource? = null
    private var genericProducts: GenericProductTestBuilderResource ? = null
    private var counterFrames: CounterFrameTestBuilderResource? = null

    override fun createClient(accessToken: String?): ApiClient {
        val result = ApiClient(TestSettings.apiBasePath)
        ApiClient.accessToken = accessToken
        return result
    }

    /**
     * Returns a test builder resource for orders
     *
     * @return a test builder resource for orders
     */
    fun orders(): OrderTestBuilderResource {
        if (orders == null) {
            orders = OrderTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return orders!!
    }

    /**
     * Returns a test builder resource for generic products
     *
     * @return a test builder resource for generic products
     */
    fun genericProducts(): GenericProductTestBuilderResource {
        if (genericProducts == null) {
            genericProducts = GenericProductTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return genericProducts!!
    }

    /**
     * Returns a test builder resource for counter frames
     *
     * @return a test builder resource for counter frames
     */
    fun counterFrames(): CounterFrameTestBuilderResource {
        if (counterFrames == null) {
            counterFrames = CounterFrameTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return counterFrames!!
    }

}