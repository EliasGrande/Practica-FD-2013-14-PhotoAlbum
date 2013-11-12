package es.udc.fi.dc.photoalbum.hibernate;

import java.util.List;

public interface FileDao extends GenericDao<File> {

    void changeAlbum(File file, Album album);

    void changePrivacyLevel(File file, String privacyLevel);

    File getById(Integer id);

    File getFileOwn(int id, String name, int userId);

    List<File> getAlbumFilesOwn(int albumId);

    List<File> getAlbumFilesOwnPaging(int albumId, int first,
            int count);

    List<File> getAlbumFilesShared(int albumId, int userId);

    List<File> getAlbumFilesSharedPaging(int albumId,
            int userId, int first, int count);

    Long getCountAlbumFiles(int albumId);

    List<File> getFilesByTag(int userId, String tag);

    List<File> getFilesByTagPaging(int userId, String tag,
            int first, int count);
}
