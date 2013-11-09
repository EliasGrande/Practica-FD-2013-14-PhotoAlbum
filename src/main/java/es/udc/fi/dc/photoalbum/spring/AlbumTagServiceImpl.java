package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTagDao;

public class AlbumTagServiceImpl implements AlbumTagService {

    private AlbumTagDao albumTagDao;

    public AlbumTagDao getAlbumTagDao() {
        return this.albumTagDao;
    }

    public void setAlbumTagDao(AlbumTagDao albumTagDao) {
        this.albumTagDao = albumTagDao;
    }

    public void create(AlbumTag albumTag) {
        if (albumTagDao.getTag(albumTag.getAlbum().getId(),
                albumTag.getTag()) == null)
            albumTagDao.create(albumTag);
    }

    public void delete(AlbumTag albumTag) {
        albumTagDao.delete(albumTag);

    }

    public AlbumTag getTag(int albumId, String tag) {
        return albumTagDao.getTag(albumId, tag);
    }

    public ArrayList<AlbumTag> getTags(int albumId) {
        return albumTagDao.getTags(albumId);
    }

}
