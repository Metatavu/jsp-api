package fi.metatavu.jsp.api.test.functional

import fi.metatavu.jsp.api.client.models.Tag
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

/**
 * Test class for testing Tags API
 *
 * @author Antti Leinonen
 */
class TagTestsIT: AbstractFunctionalTest() {
    @Test
    fun testCreateTag() {
        ApiTestBuilder().use {
            val createdTag = it.admin().tags().create()
            assertNotNull(createdTag)
            assertNotNull(createdTag.id)
            assertNotNull(createdTag.createdAt)
            assertNotNull(createdTag.creatorId)
            assertNotNull(createdTag.name)
        }
    }

    @Test
    fun testListTag() {
        ApiTestBuilder().use {
            val createdTag = it.admin().tags().create()
            val tags = it.admin().tags().listTags()
            assertEquals(1, tags.size)
            assertEquals(createdTag.id, tags[0].id)
        }
    }

    @Test
    fun testUpdateTag() {
        ApiTestBuilder().use {
            val createdTag = it.admin().tags().create()
            val tags = it.admin().tags().listTags()
            assertEquals(createdTag.name, tags[0].name)

            it.admin().tags().updateTag(Tag(
                    id = createdTag.id,
                    name = "updated name"
            ))
            assertEquals(1, it.admin().tags().listTags().size)
        }
    }

    @Test
    fun testDeleteTag() {
        ApiTestBuilder().use {
            var createdTag = it.admin().tags().create()
            var randomTagId = UUID.randomUUID()
            it.admin().tags().assertDeleteFail(404, Tag(id = randomTagId, name = "non existing tag"))
            it.admin().tags().delete(createdTag)
        }
    }
}