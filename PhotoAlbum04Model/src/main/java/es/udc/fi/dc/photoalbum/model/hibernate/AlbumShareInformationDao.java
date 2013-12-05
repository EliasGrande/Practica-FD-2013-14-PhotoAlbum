package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.List;

/**
 * DAO interface for the {@link AlbumShareInformation} entity.
 */
public interface AlbumShareInformationDao extends
        GenericDao<AlbumShareInformation> {

    /**
     * List of {@link AlbumShareInformation} related to the given
     * {@code albumId}. Used to know the list of {@link User users}
     * with whom certain {@link Album album} has been shared.
     * 
     * @param albumId
     *            {@link Album} being shared id
     * @return Share information list, it can be empty
     */
    List<AlbumShareInformation> getAlbumShares(int albumId);

    /**
     * Specific {@link AlbumShareInformation} object.
     * 
     * @param albumName
     *            {@link Album} being shared name
     * @param userSharedToId
     *           {@link User} receiver of the sharing id
     * @param userSharedEmail
     *            {@link User} owner of the album email
     * @return Share information object, or {@code null} if not found.
     */
    AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail);

    /**
     * Specific {@link AlbumShareInformation} object.
     * 
     * @param albumId
     *            {@link Album} being shared id
     * @param userId
     *            {@link User} receiver of the sharing id
     * @return Share information object, or {@code null} if not found.
     */
    AlbumShareInformation getShare(int albumId, int userId);
}
