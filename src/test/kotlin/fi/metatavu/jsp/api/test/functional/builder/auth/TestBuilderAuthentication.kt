package fi.metatavu.jsp.api.test.functional.builder.auth

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jaxrs.test.functional.builder.auth.AuthorizedTestBuilderAuthentication
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
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

    override fun createClient(accessToken: String?): ApiClient {
        val result = ApiClient(TestSettings.apiBasePath)
        ApiClient.accessToken = accessToken
        return result
    }

    fun orders(): OrderTestBuilderResource {
        if (orders == null) {
            orders = OrderTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return orders!!
    }

}