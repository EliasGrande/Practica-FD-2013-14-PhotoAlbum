package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTagDao;

/**
 * Implementation of the {@link AlbumTagService}
 */
public class AlbumTagServiceImpl implements AlbumTagService {

    /**
     * @see AlbumTagDao
     */
    private AlbumTagDao albumTagDao;

    /**
     * Method for get an {@link AlbumTagDao}.
     * 
     * @return An {@link AlbumTagDao}
     */
    public AlbumTagDao getAlbumTagDao() {
        return this.albumTagDao;
    }

    /**
     * Method that allows to put an {@link AlbumTagDao}.
     * 
     * @param albumTagDao
     *            AlbumTagDao which will be put.
     * 
     * @see AlbumTagDao
     */
    public void setAlbumTagDao(AlbumTagDao albumTagDao) {
        this.albumTagDao = albumTagDao;
    }

    /**
     * Method that allows to create an {@link AlbumTag}
     * 
     * @param albumTag
     *            {@link AlbumTag} that will be created
     * 
     * @see es.udc.fi.dc.photoalbum.spring.AlbumTagService#create(AlbumTag)
     */
    public void create(AlbumTag albumTag) {
        if (albumTagDao.getTag(albumTag.getAlbum().getId(),
                albumTag.getTag()) == null)
            albumTagDao.create(albumTag);
    }

    /**
     * Method that allows to delete an {@link AlbumTag}
     * 
     * @param albumTag
     *            {@link AlbumTag} that will be deleted
     * 
     * @see es.udc.fi.dc.photoalbum.spring.AlbumTagService#delete(AlbumTag)
     */
    public void delete(AlbumTag albumTag) {
        albumTagDao.delete(albumTag);

    }

    /**
     * Method for get an {@link AlbumTag} searching by album
     * identifier and a tag name
     * 
     * @param albumId
     *            {@link Album} identifier
     * @param tag
     *            {@link AlbumTag} tag
     * 
     * 
     * @return {@link AlbumTag} with that {@link Album} identifier and
     *         that name * @see
     *         es.udc.fi.dc.photoalbum.spring.AlbumTagService
     *         #getTag(int, String)
     */
    public AlbumTag getTag(int albumId, String tag) {
        return albumTagDao.getTag(albumId, tag);
    }

    /**
     * Get a list of {@link AlbumTag} searching by {@link Album}
     * identifier
     * 
     * @param albumId
     *            An {@link Album} identifier
     * 
     * 
     * @return List<AlbumTag> A list of {@link AlbumTag} whose
     *         {@link Album} identifier is the albumid * @see
     *         es.udc.fi
     *         .dc.photoalbum.spring.AlbumTagService#getTags(int)
     */
    public List<AlbumTag> getTags(int albumId) {
        return albumTagDao.getTags(albumId);
    }

}
