package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.List;

/**
 * DAO interface for the {@link FileTag} entity.
 */
public interface FileTagDao extends GenericDao<FileTag> {

    /**
     * Gets the {@link FileTag} identified by the given {@code fileId}
     * and the given {@code tag}.
     * 
     * @param fileId
     *            File id
     * @param tag
     *            File tag string
     * @return File tag or {@code null} if not found
     */
    FileTag getTag(int fileId, String tag);

    /**
     * Gets all the {@link FileTag} of the given {@code fileId}.
     * 
     * @param fileId
     *            File id
     * @return File tag list
     */
    List<FileTag> getTags(int fileId);
}
