package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.FileTag;

public interface FileTagService {

    void create(FileTag fileTag);

    void delete(FileTag fileTag);

    FileTag getTag(int fileId, String tag);

    List<FileTag> getTags(int fileId);
}
