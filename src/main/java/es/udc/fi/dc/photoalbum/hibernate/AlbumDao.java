package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface AlbumDao extends GenericDao<Album> {

    void rename(Album album, String newName);

    void changePrivacyLevel(Album album, String privacyLevel);

    Album getById(Integer id);

    Album getAlbum(String name, int userId);

    ArrayList<Album> getAlbums(Integer id);

    ArrayList<Album> getAlbumsSharedWith(Integer id, String ownerEmail);

    ArrayList<Album> getPublicAlbums();

    Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail);

    ArrayList<Album> getAlbumsByTag(int userId, String tag);
}
