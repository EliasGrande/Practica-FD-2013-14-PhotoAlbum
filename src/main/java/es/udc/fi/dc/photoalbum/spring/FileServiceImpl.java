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

	public ArrayList<File> getAlbumFilesOwn(int albumId) {
		return fileDao.getAlbumFilesOwn(albumId);
	}

	public ArrayList<File> getAlbumFilesOwnPaging(int albumId, int first, int count) {
		return fileDao.getAlbumFilesOwnPaging(albumId, first, count);
	}

	public Long getCountAlbumFiles(int albumId) {
		return fileDao.getCountAlbumFiles(albumId);
	}

	public void changePrivacyLevel(File file, String privacyLevel) {
		fileDao.changePrivacyLevel(file, privacyLevel);
	}

	public File getFilePublic(int id, String name, int userId) {
		return fileDao.getFilePublic(id, name, userId);
	}

	public ArrayList<File> getAlbumFilesShared(int albumId, int userId) {
		return fileDao.getAlbumFilesShared(albumId, userId);
	}

	public ArrayList<File> getAlbumFilesSharedPaging(int albumId, int userId,
			int first, int count) {
		return fileDao.getAlbumFilesSharedPaging(albumId, userId, first, count);
	}

	public ArrayList<File> getAlbumFilesPublic(int albumId, int userId) {
		return fileDao.getAlbumFilesPublic(albumId, userId);
	}

	public ArrayList<File> getAlbumFilesPublicPaging(int albumId, int userId,
			int first, int count) {
		return fileDao.getAlbumFilesPublicPaging(albumId, userId, first, count);
	}
}
