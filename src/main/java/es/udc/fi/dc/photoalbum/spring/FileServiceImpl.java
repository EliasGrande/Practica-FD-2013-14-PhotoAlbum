package es.udc.fi.dc.photoalbum.spring;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.FileDao;

import java.util.ArrayList;

@Transactional
public class FileServiceImpl implements FileService {

	private FileDao fileDao;

	public FileDao getFileDao() {
		return this.fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public void create(File file) {
		fileDao.create(file);
	}

	public void delete(File file) {
		fileDao.delete(file);
	}

	public File getFileOwn(int id, String name, int userId) {
		return fileDao.getFileOwn(id, name, userId);
	}

	public File getFileShared(int id, String name, int userId) {
		return fileDao.getFileShared(id, name, userId);
	}

	public void changeAlbum(File file, Album album) {
		fileDao.changeAlbum(file, album);
	}

	public File getById(Integer id) {
		return fileDao.getById(id);
	}

	public ArrayList<File> getAlbumFiles(int albumId) {
		return fileDao.getAlbumFiles(albumId);
	}

	public ArrayList<File> getAlbumFiles(int albumId, String minPrivacyLevel) {
		return fileDao.getAlbumFiles(albumId, minPrivacyLevel);
	}

	public ArrayList<File> getAlbumFilesPaging(int albumId, int first, int count) {
		return fileDao.getAlbumFilesPaging(albumId, first, count);
	}

	public ArrayList<File> getAlbumFilesPaging(int albumId, int first, int count, String minPrivacyLevel) {
		return fileDao.getAlbumFilesPaging(albumId, first, count, minPrivacyLevel);
	}

	public Long getCountAlbumFiles(int albumId) {
		return fileDao.getCountAlbumFiles(albumId);
	}

	public void changePrivacyLevel(File file, String privacyLevel) {
		fileDao.changePrivacy(file, privacyLevel);
	}
}
