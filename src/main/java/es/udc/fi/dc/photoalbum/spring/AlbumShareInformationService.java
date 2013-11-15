package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;

/**
 * Interface for the {@link AlbumShareInformationSservice}.
 */
public interface AlbumShareInformationService {

    /**
     * Allows to create an {@link AlbumShareInformation}
     * 
     * @param shareInformation
     *            {@link AlbumShareInformation } that will be created.
     */
    void create(AlbumShareInformation shareInformation);

    /**
     * Allows to remove an {@link AlbumShareInformation}
     * 
     * @param shareInformation
     *            {@link AlbumShareInformation } that will be removed.
     */
    void delete(AlbumShareInformation shareInformation);

    /**
     * Return an {@link AlbumShareInformation} searching by album
     * name, user target identifier and user owner email.
     * 
     * @param albumName
     *            The {@link Album} name
     * @param userSharedToId
     *            The target user
     * @param userSharedEmail
     *            The email of the user who shares the {@link Album}.
     * @return {@link AlbumShareInformation} for that search.
     */
    AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail);

    /**
     * Get a list of {@link AlbumShareInformation} from an
     * {@link Album} searching by album identifier
     * 
     * @param albumId
     *            An {@link Album} identifier
     * @return A list of {@link AlbumShareInformation}
     */
    List<AlbumShareInformation> getAlbumShares(int albumId);
}
