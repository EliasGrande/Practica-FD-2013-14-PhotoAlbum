package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface FileTagDao extends GenericDao<FileTag> {
	
	FileTag getTag(int fileId, String tag);

	ArrayList<FileTag> getTags(int fileId);
}
