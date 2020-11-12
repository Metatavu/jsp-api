package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.CounterTopsApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.infrastructure.ClientException
import fi.metatavu.jsp.api.client.models.CounterTop
import fi.metatavu.jsp.api.test.functional.settings.TestSettings
import org.junit.Assert.assertEquals
import java.util.*

class CounterTopTestBuilderResource (testBuilder: AbstractTestBuilder<ApiClient?>?, private val accessTokenProvider: AccessTokenProvider?, apiClient: ApiClient): ApiTestBuilderResource<CounterTop, ApiClient>(testBuilder, apiClient) {
    override fun clean(t: CounterTop?) {}

    /**
     * Asserts that two counter tops have identical properties
     *
     * @param expected an expected counter top
     * @param actual an actual counter top
     */
    fun assertCounterTopsEqual (expected: CounterTop, actual: CounterTop) {
        assertEquals(expected.modelName, actual.modelName)
        assertEquals(expected.thickness, actual.thickness)
        assertEquals(expected.type, actual.type)
    }

    /**
     * Sends a request to the API to list counter tops
     *
     * @return counter tops
     */
    fun list(): Array<CounterTop> {
        return api.listCounterTops()
    }

    /**
     * Sends a request to the API to find a counter top
     */
    fun find (counterTopId: UUID): CounterTop {
        return api.findCounterTop(counterTopId)
    }

    /**
     * Tests that find request fails correctly
     *
     * @param counterTopId id of a counter top to test
     * @param status expected status code
     */
    fun assertFindFailStatus (counterTopId: UUID, status: Int) {
        try {
            api.findCounterTop(counterTopId)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    override fun getApi(): CounterTopsApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return CounterTopsApi(TestSettings.apiBasePath)
    }
}