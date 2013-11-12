package es.udc.fi.dc.photoalbum.hibernate;

import java.util.List;

public interface AlbumTagDao extends GenericDao<AlbumTag> {

    AlbumTag getTag(int albumId, String tag);

    List<AlbumTag> getTags(int albumId);
}
