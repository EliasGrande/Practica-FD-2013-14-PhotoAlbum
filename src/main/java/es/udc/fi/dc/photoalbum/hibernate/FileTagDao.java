package es.udc.fi.dc.photoalbum.hibernate;

import java.util.List;

public interface FileTagDao extends GenericDao<FileTag> {

    FileTag getTag(int fileId, String tag);

    List<FileTag> getTags(int fileId);
}
