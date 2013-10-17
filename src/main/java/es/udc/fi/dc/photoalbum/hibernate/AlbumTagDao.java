package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface AlbumTagDao extends GenericDao<AlbumTag> {
	
	AlbumTag getTag(int albumId, String tag);

	ArrayList<AlbumTag> getTags(int albumId);
}
