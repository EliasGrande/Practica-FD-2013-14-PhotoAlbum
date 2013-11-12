package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Album;

public interface AlbumService {

    void create(Album album);

    void delete(Album album);

    void rename(Album album, String newName);

    void changePrivacyLevel(Album album, String privacyLevel);

    Album getById(Integer id);

    Album getAlbum(String name, int userId);

    List<Album> getAlbums(Integer id);

    List<Album> getAlbumsSharedWith(Integer id, String ownerEmail);

    List<Album> getPublicAlbums();

    Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail);

    /**
     * Get a list of albums searching by tag.
     * 
     * @param userId
     *            The user who invocated the search, needed for
     *            privacy restrictions.
     * @param tag
     *            The album tag.
     * @return A list of albums (empty if nothing found).
     */
    List<Album> getAlbumsByTag(int userId, String tag);
}
