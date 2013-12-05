package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.List;

/**
 * DAO interface for the {@link FileShareInformation} entity.
 */
public interface FileShareInformationDao extends
        GenericDao<FileShareInformation> {

    /**
     * List of {@link FileShareInformation} related to the given
     * {@code fileId}. Used to know the list of {@link User users}
     * with whom certain {@link File file} has been shared.
     * 
     * @param fileId
     *            {@link File} being shared id
     * @return Share information list, it can be empty
     */
    List<FileShareInformation> getFileShares(int fileId);

    /**
     * Specific {@link FileShareInformation} object.
     * 
     * @param fileId
     *            {@link File} being shared id
     * @param userId
     *            {@link User} receiver of the sharing id
     * @return Share information object, or {@code null} if not found.
     */
    FileShareInformation getShare(int fileId, int userId);

    /**
     * Removes all the {@link FileShareInformation} related to the
     * given {@code fileId}. After the execution of this method this
     * {@link File} is no longer being shared to any {@link User}.
     * 
     * @param fileId
     *            {@link File} being shared id
     */
    void deleteShares(int fileId);
}
