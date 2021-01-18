package fi.metatavu.jsp.orders

import fi.metatavu.jsp.api.spec.model.OrderStatus
import fi.metatavu.jsp.persistence.dao.FileDAO
import fi.metatavu.jsp.persistence.dao.OrderDAO
import fi.metatavu.jsp.persistence.model.CustomerOrder
import fi.metatavu.jsp.persistence.model.Door
import fi.metatavu.jsp.persistence.model.File
import fi.metatavu.jsp.products.*
import java.time.OffsetDateTime
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * A controller for files
 */
@ApplicationScoped
class FilesController {
    @Inject
    private lateinit var fileDAO: FileDAO

    /**
     * Lists all files
     *
     * @param customerOrder list only files belonging to this order
     * @param customerFiles list customer files or order files
     *
     * @return doors
     */
    fun list(customerOrder: CustomerOrder?, customerFiles: Boolean): List<File> {
        return fileDAO.list(customerOrder, customerFiles)
    }

    /**
     * Deletes a file
     *
     * @param customerOrder order to be deleted
     */
    fun delete (file: File) {
        fileDAO.delete(file)
    }

    /**
     * Saves file to the database
     *
     * @param name file name
     * @param url file url
     * @param order order
     * @param creatorId id of the user who creates this file
     *
     * @return a new file
     */
    fun create (name: String, url: String, customerFile: Boolean, order: CustomerOrder,
                creatorId: UUID): File {

        return fileDAO.create(UUID.randomUUID(), name, url, customerFile, order, creatorId)
    }

    /**
     * Finds a file
     *
     * @param fileId id of the door to find
     *
     * @return found file or null if not found
     */
    fun find (fileId: UUID): File? {
        return fileDAO.findById(fileId)
    }

    /**
     * Updates file
     *
     * @param file file to update
     * @param name file name to update
     * @param url url to update
     * @param modifierId id of the user who updated the file
     *
     * @return an updated file
     */
    fun update (file: File,
                name: String,
                url: String,
                modifierId: UUID): File {
        fileDAO.updateFile(file, modifierId, name, url)
        return file
    }
}
