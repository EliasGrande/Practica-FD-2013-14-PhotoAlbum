package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;

public interface FileService {

	void create(File file);

	void delete(File file);

	File getFileOwn(int id, String name, int userId);

	File getFileShared(int id, String name, int userId);

	void changeAlbum(File file, Album album);

	File getById(Integer id);

	ArrayList<File> getAlbumFiles(int albumId);

	ArrayList<File> getAlbumFilesPaging(int albumId, int first, int count);

	Long getCountAlbumFiles(int albumId);
}
