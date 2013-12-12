package es.udc.fi.dc.photoalbum.model.spring;

import java.util.Calendar;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;

/**
 * Interface for the {@link AlbumService}
 */
public interface FileService {

    /**
     * Method create. For create an {@link File}
     * 
     * @param file
     *            {@link File} that will be created
     */
    void create(File file);

    /**
     * Method delete. For delete an {@link File}
     * 
     * @param file
     *            {@link File} that will be deleted
     */
    void delete(File file);

    /**
     * Allows to change the location of a {@link File} for another
     * {@link Album}
     * 
     * @param file
     *            {@link File} whose location will be changed
     * @param album
     *            {@link Album} to which the {@link File} is moved
     */
    void changeAlbum(File file, Album album);

    /**
     * Give the possibility of change the {@link File} privacy level.
     * 
     * @param file
     *            {@link File} whose privacy level will be changed.
     * @param privacyLevel
     *            The new privacy level.
     */
    void changePrivacyLevel(File file, String privacyLevel);

    /**
     * Get {@link File} searching by id.
     * 
     * @param id
     *            Identifier for the search
     * @return {@link File} which has the identifier
     */
    File getById(Integer id);

    /**
     * Returns a {@link File} looking for a identifier, a {@link File}
     * name and an {@link User} id.
     * 
     * @param id
     *            Id for the search.
     * @param name
     *            Name of a {@link File}.
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @return {@link File} that matches the specified criteria.
     */
    File getFileOwn(int id, String name, int userId);

    /**
     * Returns a {@link File} that has been shared
     * 
     * @param id
     *            {@link File} identifier
     * @param name
     *            A name of a {@link File}
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @return File that matches the specified criteria.
     */
    File getFileShared(int id, String name, int userId);

    /**
     * Search for a file whose privacy level is public.
     * 
     * @param id
     *            {@link File} identifier
     * @param name
     *            A name of a {@link File}
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @return File whose privacy level is public.
     */
    File getFilePublic(int id, String name, int userId);

    /**
     * Search for files using an {@link Album} identifier.
     * 
     * @param albumId
     *            {@link Album} identifier for the search.
     * @return List of files that belongs to an {@link Album}.
     */
    List<File> getAlbumFilesOwn(int albumId);

    /**
     * Search for files using an {@link Album} identifier employing
     * paging
     * 
     * @param albumId
     *            {@link Album} identifier for the search.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained
     * @return List of files that belongs to an {@link Album}.
     */
    List<File> getAlbumFilesOwnPaging(int albumId, int first,
            int count);

    /**
     * Search for files from an {@link Album} which have been shared.
     * 
     * @param albumId
     *            {@link Album} identifier.
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @return A list of files which have been shared.
     */
    List<File> getAlbumFilesShared(int albumId, int userId);

    /**
     * Search for files from an {@link Album} which have been shared
     * employing paging.
     * 
     * @param albumId
     *            {@link Album} identifier.
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained.
     * @return A list of files which have been shared.
     */
    List<File> getAlbumFilesSharedPaging(int albumId, int userId,
            int first, int count);

    /**
     * Return files of an {@link Album} whose privacy level is public.
     * 
     * @param albumId
     *            {@link Album} identifier.
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @return A list of files whose privacy level is public.
     */
    List<File> getAlbumFilesPublic(int albumId, int userId);

    /**
     * Return files of an {@link Album} whose privacy level is public
     * employing paging.
     * 
     * @param albumId
     *            {@link Album} identifier.
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained.
     * @return A list of files whose privacy level is public.
     */
    List<File> getAlbumFilesPublicPaging(int albumId, int userId,
            int first, int count);

    /**
     * Give the number of files that has an {@link Album}.
     * 
     * @param albumId
     *            {@link Album} identifier for the search.
     * @return A number that represents the number of files who
     *         belongs to an {@link Album}.
     */
    Long getCountAlbumFiles(int albumId);

    /**
     * Get a list of files searching by tag.
     * 
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @param tag
     *            The file tag.
     * 
     * @return A list of files (empty if nothing found).
     */
    List<File> getFilesByTag(int userId, String tag);

    /**
     * Get a list of files searching by tag employing paging.
     * 
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @param tag
     *            The file tag.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained.
     * @return A list of files which has the tag.
     */
    List<File> getFilesByTagPaging(int userId, String tag, int first,
            int count);

    List<File> getFiles(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count);

    List<File> getFiles(String orderBy, int first, int count);

    List<File> getFiles(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count);

}
