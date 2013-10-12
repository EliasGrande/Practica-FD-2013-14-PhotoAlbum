package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;

public interface AlbumService {

	void create(Album album);

	void delete(Album album);

	void rename(Album album, String newName);

	void changePrivacyLevel(Album album, String privacyLevel);

	Album getById(Integer id);

	Album getAlbum(String name, int userId);

	ArrayList<Album> getAlbums(Integer id);

	ArrayList<Album> getAlbumsSharedWith(Integer id, String ownerEmail);

	ArrayList<Album> getPublicAlbums();
	
	Album getSharedAlbum(String albumName, int userSharedToId,
			String userSharedEmail);
}
