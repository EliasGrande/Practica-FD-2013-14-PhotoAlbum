package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;

public interface AlbumTagService {

    void create(AlbumTag albumTag);

    void delete(AlbumTag albumTag);

    AlbumTag getTag(int albumId, String tag);

    List<AlbumTag> getTags(int albumId);
}
