package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface FileTagDao extends GenericDao<FileTag> {

	ArrayList<FileTag> getTags(int fileId);
}
