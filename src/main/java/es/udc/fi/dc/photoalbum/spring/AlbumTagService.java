package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;

public interface AlbumTagService {

	void create(AlbumTag albumTag);

	void delete(AlbumTag albumTag);
	
	ArrayList<AlbumTag> getTags(int albumId);
}
