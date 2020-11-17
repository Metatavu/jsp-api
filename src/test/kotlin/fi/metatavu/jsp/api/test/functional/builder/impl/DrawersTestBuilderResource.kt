package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.DrawersApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.infrastructure.ClientException
import fi.metatavu.jsp.api.client.models.DrawersInfo
import fi.metatavu.jsp.api.test.functional.settings.TestSettings

import org.junit.Assert
import java.util.*

/**
 * A test builder resource for drawers
 */
class DrawersTestBuilderResource (testBuilder: AbstractTestBuilder<ApiClient?>?, private val accessTokenProvider: AccessTokenProvider?, apiClient: ApiClient): ApiTestBuilderResource<DrawersInfo, ApiClient>(testBuilder, apiClient) {
    override fun clean(t: DrawersInfo?) {}

    /**
     * Asserts that two drawers have identical properties
     *
     * @param expected expected drawers
     * @param actual actual drawers
     */
    fun assertDrawersEqual (expected: DrawersInfo, actual: DrawersInfo) {
        Assert.assertEquals(expected.cutleryCompartments, actual.cutleryCompartments)
        Assert.assertEquals(expected.trashbins, actual.trashbins)
        Assert.assertEquals(expected.markedInImages, actual.markedInImages)
        Assert.assertEquals(expected.additionalInformation, actual.additionalInformation)
    }

    /**
     * Sends a request to the API to list drawers
     *
     * @return drawers
     */
    fun list (): Array<DrawersInfo> {
        return api.listDrawerInfos()
    }

    /**
     * Sends a request to the API to find drawers
     *
     * @param drawersId id of the drawers to find
     *
     * @return found drawers
     */
    fun find (drawersId: UUID): DrawersInfo {
        return api.findDrawersInfo(drawersId)
    }

    /**
     * Tests that find request fails correctly
     *
     * @param drawersId id of a handle to test
     * @param status expected status code
     */
    fun assertFindFailStatus (drawersId: UUID, status: Int) {
        try {
            api.findDrawersInfo(drawersId)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    override fun getApi(): DrawersApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return DrawersApi(TestSettings.apiBasePath)
    }
}