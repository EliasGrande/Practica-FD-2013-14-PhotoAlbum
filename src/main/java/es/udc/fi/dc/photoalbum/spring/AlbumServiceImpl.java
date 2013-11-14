package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumDao;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislikeDao;

/**
 * Implementation of the album service
 */
@Transactional
public class AlbumServiceImpl implements AlbumService {

    /* AlbumDao */
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
     *            AlbumDao The AlbumDao that will be instanced
     */
    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    /* LikeAndDislike */
    private LikeAndDislikeDao likeAndDislikeDao;

    /**
     * Method getLikeAndDislikeDao. For get an instance of
     * LikeAndDislikeDao
     * 
     * @return LikeAndDislikeDao The LikeAndDislikeDao.
     */
    public LikeAndDislikeDao getLikeAndDislikeDao() {
        return this.likeAndDislikeDao;
    }

    /**
     * Method setLikeAndDislikeDao. For set an instance of
     * LikeAndDislikeDao.
     * 
     * @param likeAndDislikeDao
     *            LikeAndDislikeDao The AlbumDao that will be
     *            instanced.
     */
    public void setLikeAndDislikeDao(
            LikeAndDislikeDao likeAndDislikeDao) {
        this.likeAndDislikeDao = likeAndDislikeDao;
    }

    /**
     * Method create. For create an album
     * 
     * @param album
     *            Album The album that will be created
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService#create(Album)
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
     *            Album The album that will be deleted
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService #delete(Album)
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
     *            String The name of the album
     * @param userId
     *            int A user identifier
     * @return Album An album with that name and that user id.
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService
     *      #getAlbum(String, int)
     */
    public Album getAlbum(String name, int userId) {
        return albumDao.getAlbum(name, userId);
    }

    /**
     * Method rename. For rename an album name
     * 
     * @param album
     *            Album The album whose name will be renamed
     * @param newName
     *            String The new name for the album
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService #rename(Album,
     *      String)
     */
    public void rename(Album album, String newName) {
        albumDao.rename(album, newName);
    }

    /**
     * Method getById. For get an album searching by id
     * 
     * @param id
     *            Integer The identifier that corresponds to album id.
     * @return Album The album with that identifier.
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService
     *      #getById(Integer)
     */
    public Album getById(Integer id) {
        return albumDao.getById(id);
    }

    /**
     * Method getAlbums. For get a list of albums searching by id.
     * 
     * @param id
     *            Integer The identifier for the search.
     * @return List<Album> A list of albums.
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService
     *      #getAlbums(Integer)
     */
    public List<Album> getAlbums(Integer id) {
        return albumDao.getAlbums(id);
    }

    /**
     * Method getPublicAlbums. Get a list of public albums.
     * 
     * @return List<Album> A list of public albums.
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService
     *      #getPublicAlbums()
     */
    public List<Album> getPublicAlbums() {
        return albumDao.getPublicAlbums();
    }

    /**
     * Method changePrivacyLevel. For change the albums privacy level.
     * 
     * @param album
     *            Album The album whose privacy level will be changed
     * @param privacyLevel
     *            String The new privacy level
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService
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
     *            Integer The identifier for the search.
     * @param ownerEmail
     *            String The email of the album owner.
     * @return List<Album> A list of albums that match the specified
     *         criteria.
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService
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
     *            String The album name
     * @param userSharedToId
     *            int The target user
     * @param userSharedEmail
     *            String The email of the user who shares the album.
     * @return Album The album for that search.
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService
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
     *            int The user who invocated the search, needed for
     *            privacy restrictions.
     * @param tag
     *            String The album tag.
     * @return List<Album> A list of albums (empty if nothing found).
     * @see es.udc.fi.dc.photoalbum.spring.AlbumService
     *      #getAlbumsByTag(int, String)
     */
    public List<Album> getAlbumsByTag(int userId, String tag) {
        return albumDao.getAlbumsByTag(userId, tag);
    }

}
