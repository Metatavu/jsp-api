package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.CounterFramesApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.infrastructure.ClientException
import fi.metatavu.jsp.api.client.models.CounterFrame
import fi.metatavu.jsp.api.test.functional.settings.TestSettings
import org.junit.Assert.assertEquals
import java.util.*

/**
 * A test builder resource for counter frames
 */
class CounterFrameTestBuilderResource (testBuilder: AbstractTestBuilder<ApiClient?>?, private val accessTokenProvider: AccessTokenProvider?, apiClient: ApiClient): ApiTestBuilderResource<CounterFrame, ApiClient>(testBuilder, apiClient) {
    override fun clean(t: CounterFrame?) {}

    /**
     * Asserts that two counter frames have identical properties
     *
     * @param expected expected counter frame
     * @param actual actual counter frame
     */
    fun assertCounterFramesEqual (expected: CounterFrame, actual: CounterFrame) {
        assertEquals(expected.additionalInformation, actual.additionalInformation)
        assertEquals(expected.color, actual.color)
        assertEquals(expected.cornerStripe, actual.cornerStripe)
        assertEquals(expected.extraSide, actual.extraSide)
        assertEquals(expected.plinth, actual.plinth)
    }

    /**
     * Sends a request to the API to find a counter frame
     *
     * @param counterFrameId the id of a counter frame to find
     *
     * @return found counter frame
     */
    fun find (counterFrameId: UUID): CounterFrame {
        return api.findCounterFrame(counterFrameId)
    }

    /**
     * Tests that find request fails correctly
     *
     * @param counterFrameId an id of the counter frame to test
     * @param status expected status code
     */
    fun assertFindFailsStatus(counterFrameId: UUID, status: Int) {
        try {
            api.findCounterFrame(counterFrameId)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    /**
     * Sends a request to the API to list counter frames
     */
    fun list(): Array<CounterFrame> {
        return api.listCounterFrames()
    }

    override fun getApi(): CounterFramesApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return CounterFramesApi(TestSettings.apiBasePath)
    }
}