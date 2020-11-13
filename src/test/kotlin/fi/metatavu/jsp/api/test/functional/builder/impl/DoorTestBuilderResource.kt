package fi.metatavu.jsp.api.test.functional.builder.impl

import fi.metatavu.jaxrs.test.functional.builder.AbstractTestBuilder
import fi.metatavu.jaxrs.test.functional.builder.auth.AccessTokenProvider
import fi.metatavu.jsp.api.client.apis.DoorsApi
import fi.metatavu.jsp.api.client.infrastructure.ApiClient
import fi.metatavu.jsp.api.client.infrastructure.ClientException
import fi.metatavu.jsp.api.client.models.Door
import fi.metatavu.jsp.api.client.models.Handle
import fi.metatavu.jsp.api.test.functional.settings.TestSettings
import org.junit.Assert.assertEquals
import java.lang.Exception
import java.util.*

class DoorTestBuilderResource (testBuilder: AbstractTestBuilder<ApiClient?>?, private val accessTokenProvider: AccessTokenProvider?, apiClient: ApiClient): ApiTestBuilderResource<Door, ApiClient>(testBuilder, apiClient) {
    override fun clean(t: Door?) {}

    /**
     * Asserts that two doors have identical properties
     *
     * @param expected an expected door
     * @param actual an actual door
     */
    fun assertDoorsEqual (expected: Door, actual: Door) {
        assertEquals(expected.isGlassDoor, actual.isGlassDoor)
        assertEquals(expected.modelName, actual.modelName)
        assertEquals(expected.doorColor, actual.doorColor)
        assertEquals(expected.glassColor, actual.glassColor)
    }

    /**
     * Sends a request to the API to list doors
     *
     * @return doors
     */
    fun list (): Array<Door> {
        return api.listDoors()
    }

    /**
     * Sends a request to the API to find a door
     *
     * @param door door to find
     *
     * @return found door
     */
    fun find (door: UUID): Door {
        return api.findDoor(door)
    }

    /**
     * Tests that find request fails correctly
     *
     * @param doorId id of a door to test
     * @param status expected status code
     */
    fun assertFindFailStatus (doorId: UUID, status: Int) {
        try {
            api.findDoor(doorId)
            throw Exception("Should have failed with status $status")
        } catch (exception: ClientException) {
            assertClientExceptionStatus(status, exception)
        }
    }

    override fun getApi(): DoorsApi {
        ApiClient.accessToken = accessTokenProvider?.accessToken
        return DoorsApi(TestSettings.apiBasePath)
    }
}
