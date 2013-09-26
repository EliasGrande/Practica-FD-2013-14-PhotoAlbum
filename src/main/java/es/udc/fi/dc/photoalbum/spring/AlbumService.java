package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;

public interface AlbumService {

	void create(Album album);

	void delete(Album album);

	Album getAlbum(String name, int userId);

	void rename(Album album, String newName);

	Album getById(Integer id);

	ArrayList<Album> getAlbums(Integer id);
}
