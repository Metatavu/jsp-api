package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.client.models.GenericProductType
import fi.metatavu.jsp.api.test.functional.builder.TestBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Tests for generic products
 */
class GenericProductTestsIT: AbstractFunctionalTest() {
    @Test
    fun testFindGenericProduct() {
        TestBuilder().use { testBuilder ->
            val productId = testBuilder.admin().orders().create().sinks!![0].id
            val foundProduct = testBuilder.admin().genericProducts().find(productId!!)
            assertNotNull(foundProduct)
        }
    }

    @Test
    fun testListGenericProducts() {
        TestBuilder().use { testBuilder ->
            testBuilder.admin().orders().create()
            val products = testBuilder.admin().genericProducts().list(null)
            assertEquals(5, products.size)

            val domesticAppliances = testBuilder.admin().genericProducts().list("DOMESTIC_APPLIANCE")
            assertEquals(1, domesticAppliances.size)
        }
    }
}