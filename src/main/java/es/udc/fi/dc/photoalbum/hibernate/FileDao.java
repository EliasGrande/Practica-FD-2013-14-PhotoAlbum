package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface FileDao extends GenericDao<File> {
	
	void changeAlbum(File file, Album album);
	
	void changePrivacyLevel(File file, String privacyLevel);

	File getById(Integer id);

	File getFileOwn(int id, String name, int userId);
	
	File getFileShared(int id, String name, int userId);
	
	File getFilePublic(int id, String name, int userId);

	ArrayList<File> getAlbumFilesOwn(int albumId);

	ArrayList<File> getAlbumFilesOwnPaging(int albumId, int first, int count);

	ArrayList<File> getAlbumFilesShared(int albumId, int userId);

	ArrayList<File> getAlbumFilesSharedPaging(int albumId, int userId, int first, int count);

	ArrayList<File> getAlbumFilesPublic(int albumId, int userId);

	ArrayList<File> getAlbumFilesPublicPaging(int albumId, int userId, int first, int count);

	Long getCountAlbumFiles(int albumId);
}
