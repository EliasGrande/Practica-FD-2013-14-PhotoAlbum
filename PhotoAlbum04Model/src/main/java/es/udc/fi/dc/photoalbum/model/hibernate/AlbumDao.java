package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.Calendar;
import java.util.List;

import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

/**
 * DAO interface for the {@link Album} entity.
 */
public interface AlbumDao extends GenericDao<Album> {

    /**
     * Changes the name of the given {@link Album}.
     * 
     * @param album
     *            Album object
     * @param newName
     *            New name
     */
    void rename(Album album, String newName);

    /**
     * Changes the privacy level of the given {@link Album}.
     * 
     * @param album
     *            Album object
     * @param privacyLevel
     *            Privacy level, it must be in
     *            {@link PrivacyLevel#LIST_ALBUM}
     */
    void changePrivacyLevel(Album album, String privacyLevel);

    /**
     * Gets the {@link Album} identified by the given {@code id}.
     * 
     * @param id
     *            Album id
     * @return Album object, or {@code null} if not found
     */
    Album getById(Integer id);

    /**
     * Gets the {@link Album} which has the given {@code name} as
     * name, and is owned by the given {@code userId}.
     * 
     * @param name
     *            Album name
     * @param userId
     *            Owner {@link User user} id
     * @return Album object, or {@code null} if no album fulfills the
     *         requisites
     */
    Album getAlbum(String name, int userId);

    /**
     * List of all the {@link Album albums} owned by the given
     * {@code userId}.
     * 
     * @param userId
     *            Owner {@link User user} id
     * @return Album list
     */
    List<Album> getAlbums(Integer userId);

    /**
     * List of all the {@link Album albums} owned by the given
     * {@code ownerEmail} shared with the given
     * {@code receiverOfTheSharingId}.
     * 
     * @param userSharedToId
     *            Receiver of the sharing {@link User user} id
     * @param userSharedEmail
     *            Owner {@link User user} email
     * @return Album list
     */
    List<Album> getAlbumsSharedWith(Integer userSharedToId,
            String userSharedEmail);

    /**
     * List of all the public {@link Album albums}.
     * 
     * @return Public album list
     */
    List<Album> getPublicAlbums();

    /**
     * Same as {@link #getAlbumsSharedWith(Integer, String)} but only
     * for one album and also checking the album name.
     * 
     * @param albumName
     *            Album name.
     * @param userSharedToId
     *            Receiver of the sharing {@link User user} id
     * @param userSharedEmail
     *            Owner {@link User user} email
     * @return Album object, or {@code null} if no album fulfills the
     *         requisites
     */
    Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail);

    /**
     * List of all the {@link Album albums}, viewable by the given
     * {@link User} and tagged with the given {@link AlbumTag}.
     * 
     * @param userId
     *            User requesting the album list
     * @param tag
     *            Album tag string
     * @return Album list
     */
    List<Album> getAlbumsByTag(int userId, String tag);
    
    List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count);
    
    List<Album> getAlbums(String orderBy, int first, int count);
    
    List<Album> getAlbums(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count);
    
    List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy, int first,
            int count);
}
