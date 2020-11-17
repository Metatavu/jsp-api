package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.InstallationsApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.infrastructure.ClientException
import fi.metatavu.jsp.api.client.models.Installation
import fi.metatavu.jsp.api.test.functional.settings.TestSettings
import org.junit.Assert
import java.lang.AssertionError
import java.util.*

/**
 * A test builder resource for installations
 */
class InstallationTestBuilderResource (testBuilder: AbstractTestBuilder<ApiClient?>?, private val accessTokenProvider: AccessTokenProvider?, apiClient: ApiClient): ApiTestBuilderResource<Installation, ApiClient>(testBuilder, apiClient) {
    override fun clean(t: Installation?) {}

    /**
     * Asserts that two installations have identical properties
     */
    fun assertInstallationsEqual (expected: Installation, actual: Installation) {
        Assert.assertEquals(expected.additionalInformation, actual.additionalInformation)
        Assert.assertEquals(expected.isCustomerInstallation, actual.isCustomerInstallation)
    }

    /**
     * Sends a request to the API to list installations
     *
     * @return installations
     */
    fun list (): Array<Installation> {
        return api.listInstallations()
    }

    /**
     * sends a request to the API to find installations
     *
     * @param installationsId id of the installation to find
     *
     * @return found installations
     */
    fun find (installationsId: UUID): Installation {
        return api.findInstallation(installationsId)
    }

    /**
     * Tests that find request fails correctly
     *
     * @param installationsId id of a installation to test
     * @param status expected status code
     */
    fun assertFindFailStatus (installationsId: UUID, status: Int) {
        try {
            api.findInstallation(installationsId)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    override fun getApi(): InstallationsApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return InstallationsApi(TestSettings.apiBasePath)
    }
}

