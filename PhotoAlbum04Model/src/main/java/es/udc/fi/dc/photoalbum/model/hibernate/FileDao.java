package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.List;

import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

/**
 * DAO interface for the {@link File} entity.
 */
public interface FileDao extends GenericDao<File> {

    /**
     * Moves the given {@link File} to the given {@link Album}.
     * 
     * @param file
     *            File to move
     * @param album
     *            Album which will hold the file
     */
    void changeAlbum(File file, Album album);

    /**
     * Changes the privacy level of the given {@link File}.
     * 
     * @param file
     *            File object
     * @param privacyLevel
     *            Privacy level, it must be in
     *            {@link PrivacyLevel#LIST_FILE}
     */
    void changePrivacyLevel(File file, String privacyLevel);

    /**
     * Gets the {@link File} identified by the given {@code id}.
     * 
     * @param id
     *            File id
     * @return File object, or {@code null} if not found
     */
    File getById(Integer id);

    /**
     * Gets the {@link File} identified by the given {@code id}, but
     * also checking if is owned by the given {@link User} and its
     * {@link Album} name is the given {@code albumName}.
     * 
     * @param id
     *            File id
     * @param albumName
     *            Album name
     * @param userId
     *            Owner user id
     * @return File object, or {@code null} if not found or not owned
     *         by {@code userId} or the album holding the file is not
     *         named {@code albumName}.
     */
    File getFileOwn(int id, String albumName, int userId);

    /**
     * Gets all the files from the given {@link Album} ignoring all
     * the privacy restrictions.
     * 
     * @param albumId
     *            Album id
     * @return File list
     */
    List<File> getAlbumFilesOwn(int albumId);

    /**
     * Same as {@link #getAlbumFilesOwn(int)} but paging.
     * 
     * @param albumId
     *            Album id
     * @param first
     *            First index in the database result list
     * @param count
     *            Max number of files to return
     * @return File list
     */
    List<File> getAlbumFilesOwnPaging(int albumId, int first,
            int count);

    /**
     * Gets all the files from the given {@link Album} viewable by the
     * given {@link User}.
     * 
     * @param albumId
     *            Album id
     * @param userId
     *            User id
     * @return File list
     */
    List<File> getAlbumFilesShared(int albumId, int userId);

    /**
     * Same as {@link #getAlbumFilesShared(int, int)} but paging.
     * 
     * @param albumId
     *            Album id
     * @param userId
     *            User id
     * @param first
     *            First index in the database result list
     * @param count
     *            Max number of files to return
     * @return File list
     */
    List<File> getAlbumFilesSharedPaging(int albumId, int userId,
            int first, int count);

    /**
     * Returns the number of {@link File files} hold by the given
     * {@link Album}.
     * 
     * @param albumId
     *            Album id
     * @return File list
     */
    Long getCountAlbumFiles(int albumId);

    /**
     * List of all the {@link File files}, viewable by the given
     * {@link User} and tagged with the given {@link FileTag}.
     * 
     * @param userId
     *            User requesting the file list
     * @param tag
     *            File tag string
     * @return File list
     */
    List<File> getFilesByTag(int userId, String tag);

    /**
     * Same as {@link #getFilesByTag(int, String)} but paging.
     * 
     * @param userId
     *            User requesting the file list
     * @param tag
     *            File tag string
     * @param first
     *            First index in the database result list
     * @param count
     *            Max number of files to return
     * @return File list
     */
    List<File> getFilesByTagPaging(int userId, String tag, int first,
            int count);
}
