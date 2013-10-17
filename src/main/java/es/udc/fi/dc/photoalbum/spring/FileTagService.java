package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;
import es.udc.fi.dc.photoalbum.hibernate.FileTag;

public interface FileTagService {

	void create(FileTag fileTag);

	void delete(FileTag fileTag);
	
	ArrayList<FileTag> getTags(int fileId);
}
