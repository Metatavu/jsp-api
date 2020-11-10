package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.HandlesApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.infrastructure.ClientException
import fi.metatavu.jsp.api.client.models.Handle
import fi.metatavu.jsp.api.test.functional.settings.TestSettings
import org.junit.Assert.assertEquals
import java.util.*

class HandleTestBuilderResource (testBuilder: AbstractTestBuilder<ApiClient?>?, private val accessTokenProvider: AccessTokenProvider?, apiClient: ApiClient): ApiTestBuilderResource<Handle, ApiClient>(testBuilder, apiClient) {
    override fun clean(t: Handle?) {}

    /**
     * Asserts that two handles have identical properties
     *
     * @param expected an expected handle
     * @param actual an actual handle
     */
    fun assertHandlesEqual (expected: Handle, actual: Handle) {
        assertEquals(expected.color, actual.color)
        assertEquals(expected.doorModelName, actual.doorModelName)
        assertEquals(expected.markedInImages, actual.markedInImages)
    }

    /**
     * Sends a request to the API to list handles
     */
    fun list (): Array<Handle> {
        return api.listHandles()
    }

    /**
     * Sends a request to the API to find a handle
     */
    fun find (handleId: UUID): Handle {
        return api.findHandle(handleId)
    }

    /**
     * Tests that find request fails correctly
     *
     * @param handleId id of a handle to test
     * @param status expected status code
     */
    fun assertFindFailStatus (handleId: UUID, status: Int) {
        try {
            api.findHandle(handleId)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    override fun getApi(): HandlesApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return HandlesApi(TestSettings.apiBasePath)
    }
}