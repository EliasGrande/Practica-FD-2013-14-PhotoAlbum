package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Album;

/**
 * Interface for the Album service.
 */
public interface AlbumService {

    /**
     * Method create. For create an album
     * @param album Album
     *          The album that will be created
     */
    void create(Album album);

    /**
     * Method delete. For delete an album
     * @param album Album
     *          The album that will be deleted
     */
    void delete(Album album);

    /**
     * Method rename. For rename an album name
     * @param album Album
     *          The album whose name will be renamed
     * @param newName String
     *          The new name for the album
     */
    void rename(Album album, String newName);

    /**
     * Method changePrivacyLevel. For change the albums privacy 
     * level.
     * @param album Album
     *          The album whose privacy level will be changed
     * @param privacyLevel String
     *          The new privacy level
     */
    void changePrivacyLevel(Album album, String privacyLevel);

    /**
     * Method getById. For get an album searching by id
     * @param id Integer
     *          The identifier that corresponds to album id 
     * @return Album
     *          The album with that identifier.
     */
    Album getById(Integer id);

    /**
     * Method getAlbum. For get an album searching by name
     * and user identifier
     * @param name String
     *          The name of the album
     * @param userId int
     *          A user identifier
     * @return Album
     *          An album with that name and that user id.
     */
    Album getAlbum(String name, int userId);

    /**
     * Method getAlbums. For get a list of albums searching
     * by id.
     * @param id Integer
     *          The identifier for the search.
     * @return List<Album>
     *          A list of albums.
     */
    List<Album> getAlbums(Integer id);

    /**
     * Method getAlbumsSharedWith. Get albums searching by id and
     * the email of the albums owner.
     * @param id Integer
     *          The identifier for the search.
     * @param ownerEmail String
     *          The email of the album owner.
     * @return List<Album>
     *          A list of albums that match the specified criteria.
     */
    List<Album> getAlbumsSharedWith(Integer id, String ownerEmail);

    /**
     * Method getPublicAlbums. Get a list of public albums.
     * @return List<Album>
     *          A list of public albums.
     */
    List<Album> getPublicAlbums();

    /**
     * Method getSharedAlbum. Get an album searching by album 
     * name, owner user and target user
     * @param albumName String
     *          The album name
     * @param userSharedToId int
     *          The target user
     * @param userSharedEmail String
     *          The email of the user who shares the album.
     * @return Album
     *          The album for that search.
     */
    Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail);

    /**
     * Get a list of albums searching by tag.
     * 
     * @param userId
     *            The user who invocated the search, needed for
     *            privacy restrictions.
     * @param tag
     *            The album tag.
     * 
     * @return A list of albums (empty if nothing found).
     */
    List<Album> getAlbumsByTag(int userId, String tag);
}
