package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.hibernate.FileTagDao;

public class FileTagServiceImpl implements FileTagService {

	private FileTagDao fileTagDao;

	public FileTagDao getFileTagDao() {
		return this.fileTagDao;
	}

	public void setFileTagDao(FileTagDao fileTagDao) {
		this.fileTagDao = fileTagDao;
	}

	public void create(FileTag fileTag) {
		if (fileTagDao.getTag(fileTag.getFile().getId(), fileTag.getTag()) == null)
			fileTagDao.create(fileTag);

	}

	public void delete(FileTag fileTag) {
		fileTagDao.delete(fileTag);

	}

	public FileTag getTag(int fileId, String tag) {
		return fileTagDao.getTag(fileId, tag);
	}

	public ArrayList<FileTag> getTags(int fileId) {
		return fileTagDao.getTags(fileId);
	}

}
