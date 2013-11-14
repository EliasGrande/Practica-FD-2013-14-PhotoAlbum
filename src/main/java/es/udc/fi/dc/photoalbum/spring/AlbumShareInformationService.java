package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;

/**
 * Interface for the AlbumShareInformation service.
 */
public interface AlbumShareInformationService {

    /**
     * Method create. Create an AlbumShareInformation
     * @param shareInformation AlbumShareInformation
     *          The album share information that will be created
     */
    void create(AlbumShareInformation shareInformation);

    /**
     * Method delete. Delete an AlbumShareInformation
     * @param shareInformation AlbumShareInformation
     *          The album share information that will be removed
     */
    void delete(AlbumShareInformation shareInformation);

    /**
     * Method getShare. Get an album share information searching
     * by album name, user target identifier and user owner
     * email.
     * @param albumName String
     *          The album name
     * @param userSharedToId int
     *          The target user
     * @param userSharedEmail String
     *          The email of the user who shares the album.
     * @return AlbumShareInformation
     *          The album share information for that search.
     */
    AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail);

    /**
     * Method getAlbumShares. Get a list of album share information
     * from an album searching by album identifier
     * @param albumId int
     *          The album identifier
     * @return List<AlbumShareInformation>
     *          The list of album share information
     */
    List<AlbumShareInformation> getAlbumShares(int albumId);
}
