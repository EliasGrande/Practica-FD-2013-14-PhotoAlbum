package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;

public interface FileService {

    void create(File file);

    void delete(File file);

    void changeAlbum(File file, Album album);

    void changePrivacyLevel(File file, String privacyLevel);

    File getById(Integer id);

    File getFileOwn(int id, String name, int userId);

    File getFileShared(int id, String name, int userId);

    File getFilePublic(int id, String name, int userId);

    List<File> getAlbumFilesOwn(int albumId);

    List<File> getAlbumFilesOwnPaging(int albumId, int first,
            int count);

    List<File> getAlbumFilesShared(int albumId, int userId);

    List<File> getAlbumFilesSharedPaging(int albumId,
            int userId, int first, int count);

    List<File> getAlbumFilesPublic(int albumId, int userId);

    List<File> getAlbumFilesPublicPaging(int albumId,
            int userId, int first, int count);

    Long getCountAlbumFiles(int albumId);

    /**
     * Get a list of files searching by tag.
     * 
     * @param userId
     *            The user who invocated the search, needed for
     *            privacy restrictions.
     * @param tag
     *            The file tag.
     * @return A list of files (empty if nothing found).
     */
    List<File> getFilesByTag(int userId, String tag);

    List<File> getFilesByTagPaging(int userId, String tag,
            int first, int count);

}
