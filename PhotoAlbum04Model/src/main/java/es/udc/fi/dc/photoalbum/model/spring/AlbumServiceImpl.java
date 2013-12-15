package es.udc.fi.dc.photoalbum.model.spring;

import java.util.Calendar;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumDao;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislikeDao;

/**
 * Implementation of the {@link AlbumService}.
 */
@Transactional
public class AlbumServiceImpl implements AlbumService {

    /**
     * @see AlbumDao
     */
    private AlbumDao albumDao;

    /**
     * Method getAlbumDao. For get an instance of AlbumDao
     * 
     * @return AlbumDao The AlbumDao
     */
    public AlbumDao getAlbumDao() {
        return this.albumDao;
    }

    /**
     * Method setAlbumDao. For set an instance of AlbumDao
     * 
     * @param albumDao
     *            AlbumDao that will be instanced
     */
    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    /**
     * @see LikeAndDislikeDao
     */
    private LikeAndDislikeDao likeAndDislikeDao;

    /**
     * Method getLikeAndDislikeDao. For get an instance of
     * LikeAndDislikeDao
     * 
     * @return A LikeAndDislikeDao.
     */
    public LikeAndDislikeDao getLikeAndDislikeDao() {
        return this.likeAndDislikeDao;
    }

    /**
     * Method setLikeAndDislikeDao. For set an instance of
     * LikeAndDislikeDao.
     * 
     * @param likeAndDislikeDao
     *            LikeAndDislikeDaothat will be instanced.
     */
    public void setLikeAndDislikeDao(
            LikeAndDislikeDao likeAndDislikeDao) {
        this.likeAndDislikeDao = likeAndDislikeDao;
    }

    /**
     * Method create. For create an album
     * 
     * @param album
     *            Album that will be created
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService#create(Album)
     */
    public void create(Album album) {
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        likeAndDislikeDao.create(likeAndDislike);
        album.setLikeAndDislike(likeAndDislike);
        albumDao.create(album);
    }

    /**
     * Method delete. For delete an album
     * 
     * @param album
     *            Album that will be deleted
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService #delete(Album)
     */
    public void delete(Album album) {
        LikeAndDislike lad = album.getLikeAndDislike();
        albumDao.delete(album);
        likeAndDislikeDao.delete(lad);
    }

    /**
     * Method getAlbum. For get an album searching by name and user
     * identifier
     * 
     * @param name
     *            The name of the album
     * @param userId
     *            A user identifier
     * @return Album An album with that name and that user id.
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService
     *      #getAlbum(String, int)
     */
    public Album getAlbum(String name, int userId) {
        return albumDao.getAlbum(name, userId);
    }

    /**
     * Method rename. For rename an album name
     * 
     * @param album
     *            The album whose name will be renamed
     * @param newName
     *            The new name for the album
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService #rename(Album,
     *      String)
     */
    public void rename(Album album, String newName) {
        albumDao.rename(album, newName);
    }

    /**
     * Method getById. For get an album searching by id
     * 
     * @param id
     *            The identifier that corresponds to album id.
     * @return The album with that identifier.
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService
     *      #getById(Integer)
     */
    public Album getById(Integer id) {
        return albumDao.getById(id);
    }

    /**
     * Method getAlbums. For get a list of albums searching by id.
     * 
     * @param id
     *            The identifier for the search.
     * @return List<Album> with that id
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService
     *      #getAlbums(Integer)
     */
    public List<Album> getAlbums(Integer id) {
        return albumDao.getAlbums(id);
    }

    /**
     * Method getPublicAlbums. Get a list of public albums.
     * 
     * @return List<Album> of public albums.
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService
     *      #getPublicAlbums()
     */
    public List<Album> getPublicAlbums() {
        return albumDao.getPublicAlbums();
    }

    /**
     * Method changePrivacyLevel. For change the albums privacy level.
     * 
     * @param album
     *            The album whose privacy level will be changed
     * @param privacyLevel
     *            The new privacy level
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService
     *      #changePrivacyLevel(Album, String)
     */
    public void changePrivacyLevel(Album album, String privacyLevel) {
        albumDao.changePrivacyLevel(album, privacyLevel);
    }

    /**
     * Method getAlbumsSharedWith. Get albums searching by id and the
     * email of the albums owner.
     * 
     * @param id
     *            The identifier for the search.
     * @param ownerEmail
     *            The email of the album owner.
     * @return A list of albums that match the specified criteria.
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService
     *      #getAlbumsSharedWith(Integer, String)
     */
    public List<Album> getAlbumsSharedWith(Integer id,
            String ownerEmail) {
        return albumDao.getAlbumsSharedWith(id, ownerEmail);
    }

    /**
     * Method getSharedAlbum. Get an album searching by album name,
     * owner user and target user.
     * 
     * @param albumName
     *            The album name
     * @param userSharedToId
     *            The target user
     * @param userSharedEmail
     *            The email of the user who shares the album.
     * @return The album for that search.
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService
     *      #getSharedAlbum(String, int, String)
     */
    public Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail) {
        return albumDao.getSharedAlbum(albumName, userSharedToId,
                userSharedEmail);
    }

    /**
     * Get a list of albums searching by tag.
     * 
     * @param userId
     *            The user who invocated the search, needed for
     *            privacy restrictions.
     * @param tag
     *            The album tag.
     * @return A list of albums (empty if nothing found).
     * @see es.udc.fi.dc.photoalbum.model.spring.AlbumService
     *      #getAlbumsByTag(int, String)
     */
    public List<Album> getAlbumsByTag(int userId, String tag) {
        return albumDao.getAlbumsByTag(userId, tag);
    }

    @Override
    public List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Album> getAlbums(String orderBy, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Album> getAlbums(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy, int first,
            int count) {
        // TODO Auto-generated method stub
        return null;
    }

}
