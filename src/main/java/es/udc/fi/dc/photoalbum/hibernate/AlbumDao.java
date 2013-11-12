package es.udc.fi.dc.photoalbum.hibernate;

import java.util.List;

public interface AlbumDao extends GenericDao<Album> {

    void rename(Album album, String newName);

    void changePrivacyLevel(Album album, String privacyLevel);

    Album getById(Integer id);

    Album getAlbum(String name, int userId);

    List<Album> getAlbums(Integer id);

    List<Album> getAlbumsSharedWith(Integer id, String ownerEmail);

    List<Album> getPublicAlbums();

    Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail);

    List<Album> getAlbumsByTag(int userId, String tag);
}
