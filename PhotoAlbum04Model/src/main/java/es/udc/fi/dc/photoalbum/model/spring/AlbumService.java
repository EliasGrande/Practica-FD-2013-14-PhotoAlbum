package es.udc.fi.dc.photoalbum.model.spring;

import java.util.Calendar;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.model.hibernate.Comment;
import es.udc.fi.dc.photoalbum.model.hibernate.User;

/**
 * Interface for the {@link AlbumService}
 */
public interface AlbumService {

    /**
     * Method create. For create an {@link Album}
     * 
     * @param album
     *            {@link Album} that will be created
     */
    void create(Album album);

    /**
     * Method delete. For delete an {@link Album}
     * 
     * @param album
     *            {@link Album} that will be deleted
     */
    void delete(Album album);

    /**
     * Method rename. For rename an {@link Album} name
     * 
     * @param album
     *            {@link Album} whose name will be renamed
     * @param newName
     *            The new name for the {@link Album}
     */
    void rename(Album album, String newName);

    /**
     * Method changePrivacyLevel. For change the {@link Album} privacy
     * level.
     * 
     * @param album
     *            {@link Album} whose privacy level will be changed
     * @param privacyLevel
     *            The new privacy level
     */
    void changePrivacyLevel(Album album, String privacyLevel);

    /**
     * Method getById. For get an {@link Album} searching by
     * {@link Album} identifier
     * 
     * @param id
     *            The identifier that corresponds to {@link Album} id
     * 
     * @return {@link Album} with that identifier.
     */
    Album getById(Integer id);

    /**
     * Method getAlbum. For get an {@link Album} searching by name and
     * {@link User} identifier
     * 
     * @param name
     *            The name of the {@link Album}
     * @param userId
     *            An {@link User} identifier
     * 
     * @return An {@link Album} with that name and that {@link User}
     *         id.
     */
    Album getAlbum(String name, int userId);

    /**
     * Method getAlbums. For get a list of {@link Album} searching by
     * id.
     * 
     * @param id
     *            The identifier for the search.
     * 
     * @return List<Album> A list of {@link Album}
     */
    List<Album> getAlbums(Integer id);

    /**
     * Method getAlbumsSharedWith. Get albums searching by id and the
     * email of the albums owner.
     * 
     * @param id
     *            The identifier for the search.
     * @param ownerEmail
     *            The email of the album owner.
     * 
     * @return List<Album> A list of albums that match the specified
     *         criteria.
     */
    List<Album> getAlbumsSharedWith(Integer id, String ownerEmail);

    /**
     * Method getPublicAlbums. Get a list of public albums.
     * 
     * 
     * @return List<Album> A list of public albums.
     */
    List<Album> getPublicAlbums();

    /**
     * Method getSharedAlbum. Get an {@link Album} searching by
     * {@link Album} name, owner {@link User} and target {@link User}
     * 
     * @param albumName
     *            The {@link Album} name
     * @param userSharedToId
     *            The target {@link User}
     * @param userSharedEmail
     *            The email of the {@link User} who shares the
     *            {@link Album}.
     * 
     * @return Album The album for that search.
     */
    Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail);

    /**
     * Get a list of {@link Album} searching by tag.
     * 
     * @param userId
     *            The {@link User} who invocated the search, needed
     *            for privacy restrictions.
     * @param tag
     *            The {@link Album} tag.
     * 
     * 
     * @return A list of {@link Album} (empty if nothing found).
     */
    List<Album> getAlbumsByTag(int userId, String tag);

    /**
     * Search albums by keywords by performing the search by album
     * name, or text in a text comment or a tag between two dates
     * employing paging.
     * 
     * @param keywords
     *            Keywords with which the search is performed.
     * @param name
     *            Boolean indicating that the search is done by name
     *            {@link Album}.
     * @param comment
     *            Boolean indicating that the search is done by
     *            {@link Comment} text.
     * @param tag
     *            Boolean indicating that the search is done by
     *            {@link AlbumTag} text.
     * @param orderBy
     *            Indicates the order in which the search is
     *            performed: date, like, dislike.
     * @param fechaMin
     *            Minimum date search.
     * @param fechaMax
     *            Maximum date search.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained.
     * @return A list of albums that match the search parameters.
     */
    List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count);

    /**
     * Find albums ordered by date, number of likes or number of
     * dislikes employing paging.
     * 
     * @param orderBy
     *            Indicates the order in which the search is
     *            performed: date, like, dislike.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained.
     * @return A list of albums that match the search parameters.
     */
    List<Album> getAlbums(String orderBy, int first, int count);

    /**
     * Find albums ordered by date, number of likes or dislikes and
     * number who are between two dates employing paging.
     * 
     * @param orderBy
     *            Indicates the order in which the search is
     *            performed: date, like, dislike.
     * @param fechaMin
     *            Minimum date search.
     * @param fechaMax
     *            Maximum date search.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained.
     * @return A list of albums that match the search parameters.
     */
    List<Album> getAlbums(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count);

    /**
     * Search albums by keywords by performing the search by album
     * name, or text in a text comment or a tag employing paging.
     * 
     * @param keywords
     *            Keywords with which the search is performed.
     * @param name
     *            Boolean indicating that the search is done by name
     *            {@link Album}.
     * @param comment
     *            Boolean indicating that the search is done by
     *            comment text.
     * @param tag
     *            Boolean indicating that the search is done by tag
     *            text.
     * @param orderBy
     *            Indicates the order in which the search is
     *            performed: date, like, dislike.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained.
     * @return A list of albums that match the search parameters.
     */
    List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy, int first,
            int count);
}
