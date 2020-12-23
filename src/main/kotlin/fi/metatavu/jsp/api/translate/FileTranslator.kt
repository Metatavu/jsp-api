package fi.metatavu.jsp.api.translate

import fi.metatavu.jsp.api.spec.model.FileInformation

/**
 * A translator class for file
 */
class FileTranslator: AbstractTranslator<fi.metatavu.jsp.persistence.model.File, FileInformation>() {

    /**
     * Translated JPA file into REST fileinformation
     *
     * @param entity a file to translate
     *
     * @return translated doors
     */
    override fun translate(entity: fi.metatavu.jsp.persistence.model.File): FileInformation {
        val file = FileInformation()
        file.id = entity.id
        file.name = entity.name
        file.url = entity.url
        file.createdAt = entity.createdAt
        file.creatorId = entity.creatorId
        file.modifiedAt = entity.modifiedAt
        file.modifierId = entity.lastModifierId

        return file
    }

}