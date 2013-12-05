package es.udc.fi.dc.photoalbum.model.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.FileTag;

/**
 * Interface for the {@link FileTagService}
 */
public interface FileTagService {

    /**
     * Method that allows to create an {@link FileTag}.
     * 
     * @param fileTag
     *            {@link FileTag} that will be created.
     */
    void create(FileTag fileTag);

    /**
     * Method that allows to delete an {@link FileTag}.
     * 
     * @param fileTag
     *            {@link FileTag} that will be deleted.
     */
    void delete(FileTag fileTag);

    /**
     * Method for get an {@link FileTag} searching by {@link File}
     * identifier and a tag name.
     * 
     * @param fileId
     *            {@link File} identifier.
     * @param tag
     *            {@link FileTag} tag.
     * @return {@link FileTag} object that corresponds with that
     *         {@link File} identifier and tag.
     */
    FileTag getTag(int fileId, String tag);

    /**
     * Get a list of {@link FileTag} searching by {@link File}
     * identifier.
     * 
     * @param fileId
     *            An {@link File} identifier.
     * @return A list of {@link FileTag} whose {@link File} identifier
     *         is the {@link File} id.
     */
    List<FileTag> getTags(int fileId);
}
