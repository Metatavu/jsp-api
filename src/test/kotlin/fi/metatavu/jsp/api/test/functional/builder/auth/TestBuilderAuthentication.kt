package fi.metatavu.jsp.api.test.functional.builder.auth

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jaxrs.test.functional.builder.auth.AuthorizedTestBuilderAuthentication
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.test.functional.builder.impl.*
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
    private var genericProducts: GenericProductTestBuilderResource? = null
    private var handles: HandleTestBuilderResource? = null
    private var counterFrames: CounterFrameTestBuilderResource? = null
    private var doors: DoorTestBuilderResource? = null
    private var counterTops: CounterTopTestBuilderResource? = null
    private var drawers: DrawersTestBuilderResource? = null
    private var installations: InstallationTestBuilderResource? = null

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
     * Returns a test builder resource for handles
     *
     * @return a test builder resource for handles
     */
    fun handles(): HandleTestBuilderResource {
        if (handles == null) {
            handles = HandleTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return handles!!
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

    /**
     * Returns a test builder resource for doors
     *
     * @return a test builder resource for doors
     */
    fun doors(): DoorTestBuilderResource {
        if (doors == null) {
            doors = DoorTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return doors!!
    }

    /**
     * Returns a test builder resource for counter tops
     *
     * @return a test builder resource for counter tops
     */
    fun counterTops(): CounterTopTestBuilderResource {
        if (counterTops == null) {
            counterTops = CounterTopTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return counterTops!!
    }

    /**
     * Returns a test builder resource for drawers
     *
     * @return a test builder resource for drawers
     */
    fun drawers(): DrawersTestBuilderResource {
        if (drawers == null) {
            drawers = DrawersTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return drawers!!
    }

    /**
     * Returns a test builder resource for installations
     *
     * @return a test builder resource for installations
     */
    fun installations(): InstallationTestBuilderResource {
        if (installations == null) {
            installations = InstallationTestBuilderResource(testBuilder, accessTokenProvider, createClient())
        }

        return installations!!
    }
}
