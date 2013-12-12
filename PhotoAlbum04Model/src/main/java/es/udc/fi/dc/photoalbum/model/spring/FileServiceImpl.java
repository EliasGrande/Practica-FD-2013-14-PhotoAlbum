package es.udc.fi.dc.photoalbum.model.spring;

import java.util.Calendar;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumDao;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumShareInformationDao;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.FileDao;
import es.udc.fi.dc.photoalbum.model.hibernate.FileShareInformationDao;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislikeDao;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

/**
 * Implementation of the {@link FileService}
 */
@Transactional
public class FileServiceImpl implements FileService {

    /**
     * @see LikeAndDislikeDao
     */
    private LikeAndDislikeDao likeAndDislikeDao;

    /**
     * Method for get a LikeAndDislikeDao.
     * 
     * @return LikeAndDislikeDao
     */
    public LikeAndDislikeDao getLikeAndDislikeDao() {
        return this.likeAndDislikeDao;
    }

    /**
     * Method to set a LikeAndDislikeDao.
     * 
     * @param likeAndDislikeDao
     *            LikeAndDislikeDao instance
     */
    public void setLikeAndDislikeDao(
            LikeAndDislikeDao likeAndDislikeDao) {
        this.likeAndDislikeDao = likeAndDislikeDao;
    }

    /**
     * @see FileDao
     */
    private FileDao fileDao;

    /**
     * Allows to set a FileDao.
     * 
     * @return FileDao
     */
    public FileDao getFileDao() {
        return this.fileDao;
    }

    /**
     * Allows to set a FileDao.
     * 
     * @param fileDao
     *            FileDao instance
     */
    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    /**
     * @see AlbumDao
     */
    private AlbumDao albumDao;

    /**
     * Allows to get a AlbumDao.
     * 
     * @return AlbumDao
     */
    public AlbumDao getAlbumDao() {
        return this.albumDao;
    }

    /**
     * Allows to set a AlbumDao.
     * 
     * @param albumDao
     *            AlbumDao instance
     */
    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    /**
     * @see AlbumShareInformationDao
     */
    private AlbumShareInformationDao albumShareInformationDao;

    /**
     * Allows to set a AlbumShareInformationDao.
     * 
     * @return AlbumShareInformationDao
     */
    public AlbumShareInformationDao getAlbumShareInformationDao() {
        return this.albumShareInformationDao;
    }

    /**
     * Allows to set a AlbumShareInformationDao.
     * 
     * @param albumShareInformationDao
     *            AlbumShareInformationDao instance.
     */
    public void setAlbumShareInformationDao(
            AlbumShareInformationDao albumShareInformationDao) {
        this.albumShareInformationDao = albumShareInformationDao;
    }

    /**
     * @see FileShareInformationDao
     */
    private FileShareInformationDao fileShareInformationDao;

    /**
     * Allows to get a FileShareInformationDao.
     * 
     * @return FileShareInformationDao
     */
    public FileShareInformationDao getFileShareInformationDao() {
        return this.fileShareInformationDao;
    }

    /**
     * Allows to set a FileShareInformationDao.
     * 
     * @param fileShareInformationDao
     *            FileShareInformationDao instance.
     */
    public void setFileShareInformationDao(
            FileShareInformationDao fileShareInformationDao) {
        this.fileShareInformationDao = fileShareInformationDao;
    }

    /**
     * Method to create an {@link File}
     * 
     * @param file
     *            {@link File} that will be created
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#create(File)
     */
    public void create(File file) {
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        likeAndDislikeDao.create(likeAndDislike);
        file.setLikeAndDislike(likeAndDislike);
        fileDao.create(file);
    }

    /**
     * Method to delete an {@link File}
     * 
     * @param file
     *            {@link File} that will be deleted
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#delete(File)
     */
    public void delete(File file) {
        LikeAndDislike lad = file.getLikeAndDislike();
        fileDao.delete(file);
        likeAndDislikeDao.delete(lad);
    }

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
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getFileOwn(int,
     *      String, int)
     */
    public File getFileOwn(int id, String name, int userId) {
        return fileDao.getFileOwn(id, name, userId);
    }

    /**
     * Returns a {@link File} that has been shared
     * 
     * @param fileId
     *            {@link File} identifier
     * @param name
     *            A name of a {@link File}
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @return File that matches the specified criteria.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getFileShared(int,
     *      String, int)
     */
    public File getFileShared(int fileId, String name, int userId) {
        // get file
        File file = fileDao.getById(fileId);
        if (file == null)
            return null;

        String filePrivacyLevel = file.getPrivacyLevel();

        // the file is public => return it
        if (filePrivacyLevel.equals(PrivacyLevel.PUBLIC))
            return file;

        // the file is private => check FileShareInformation
        if (filePrivacyLevel.equals(PrivacyLevel.PRIVATE)) {
            return (fileShareInformationDao.getShare(fileId, userId) == null) ? null
                    : file;
        }

        // the file inherit its share information from the album
        // => check AlbumShareInformation
        if (filePrivacyLevel.equals(PrivacyLevel.INHERIT_FROM_ALBUM)) {
            int albumId = file.getAlbum().getId();
            return (albumShareInformationDao
                    .getShare(albumId, userId) == null) ? null : file;
        }

        // unknown privacy level => return nothing
        return null;
    }

    /**
     * Allows to change the location of a {@link File} for another
     * {@link Album}
     * 
     * @param file
     *            {@link File} whose location will be changed
     * @param album
     *            {@link Album} to which the {@link File} is moved
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#changeAlbum(File,
     *      Album)
     */
    public void changeAlbum(File file, Album album) {
        fileDao.changeAlbum(file, album);
    }

    /**
     * Get {@link File} searching by id.
     * 
     * @param id
     *            Identifier for the search
     * @return {@link File} which has the identifier
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getById(Integer)
     */
    public File getById(Integer id) {
        return fileDao.getById(id);
    }

    /**
     * Search for files using an {@link Album} identifier.
     * 
     * @param albumId
     *            {@link Album} identifier for the search.
     * @return List of files that belongs to an {@link Album}.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getAlbumFilesOwn(int)
     */
    public List<File> getAlbumFilesOwn(int albumId) {
        return fileDao.getAlbumFilesOwn(albumId);
    }

    /**
     * Search for files using an {@link Album} identifier employing
     * paging.
     * 
     * @param albumId
     *            {@link Album} identifier for the search.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained
     * @return List of files that belongs to an {@link Album}.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getAlbumFilesOwnPaging(int,
     *      int, int)
     */
    public List<File> getAlbumFilesOwnPaging(int albumId, int first,
            int count) {
        return fileDao.getAlbumFilesOwnPaging(albumId, first, count);
    }

    /**
     * Give the number of files that has an {@link Album}.
     * 
     * @param albumId
     *            {@link Album} identifier for the search.
     * @return A number that represents the number of files who
     *         belongs to an {@link Album}.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getCountAlbumFiles(int)
     */
    public Long getCountAlbumFiles(int albumId) {
        return fileDao.getCountAlbumFiles(albumId);
    }

    /**
     * Give the possibility of change the {@link File} privacy level.
     * 
     * @param file
     *            {@link File} whose privacy level will be changed.
     * @param privacyLevel
     *            The new privacy level.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#changePrivacyLevel(File,
     *      String)
     */
    public void changePrivacyLevel(File file, String privacyLevel) {
        fileDao.changePrivacyLevel(file, privacyLevel);
        if (privacyLevel.equals(PrivacyLevel.INHERIT_FROM_ALBUM))
            fileShareInformationDao.deleteShares(file.getId());
    }

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
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getFilePublic(int,
     *      String, int)
     */
    public File getFilePublic(int id, String name, int userId) {
        // try to get it as owner
        File file = fileDao.getFileOwn(id, name, userId);
        if (file == null)
            // try to get it as "shared with me" or "public file"
            file = getFileShared(id, name, userId);
        return file;
    }

    /**
     * Search for files from an {@link Album} which have been shared.
     * 
     * @param albumId
     *            {@link Album} identifier.
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @return A list of files which have been shared.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getAlbumFilesShared(int,
     *      int)
     */
    public List<File> getAlbumFilesShared(int albumId, int userId) {
        return fileDao.getAlbumFilesShared(albumId, userId);
    }

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
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getAlbumFilesSharedPaging(int,
     *      int, int, int)
     */
    public List<File> getAlbumFilesSharedPaging(int albumId,
            int userId, int first, int count) {
        return fileDao.getAlbumFilesSharedPaging(albumId, userId,
                first, count);
    }

    /**
     * Return files of an {@link Album} whose privacy level is public.
     * 
     * @param albumId
     *            {@link Album} identifier.
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @return A list of files whose privacy level is public.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getAlbumFilesPublic(int,
     *      int)
     */
    public List<File> getAlbumFilesPublic(int albumId, int userId) {
        Album album = albumDao.getById(albumId);
        if (album == null)
            return null;
        if (album.getUser().getId() == userId) {
            // I'm the owner, show all the files of the album
            return getAlbumFilesOwn(albumId);
        } else {
            // I'm not the owner, show all public and files shared
            // with me
            return getAlbumFilesShared(albumId, userId);
        }
    }

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
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getAlbumFilesPublicPaging(int,
     *      int, int, int)
     */
    public List<File> getAlbumFilesPublicPaging(int albumId,
            int userId, int first, int count) {
        Album album = albumDao.getById(albumId);
        if (album == null)
            return null;
        if (album.getUser().getId() == userId) {
            // I'm the owner, show all the files of the album
            return getAlbumFilesOwnPaging(albumId, first, count);
        } else {
            // I'm not the owner, show all public and files shared
            // with me
            return getAlbumFilesSharedPaging(albumId, userId, first,
                    count);
        }
    }

    /**
     * Get a list of files searching by tag.
     * 
     * @param userId
     *            {@link User} who invokes the search, needed for
     *            privacy restrictions.
     * @param tag
     *            The file tag.
     * @return A list of files (empty if nothing found).
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getFilesByTag(int,
     *      String)
     */
    public List<File> getFilesByTag(int userId, String tag) {
        return fileDao.getFilesByTag(userId, tag);
    }

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
     * @see es.udc.fi.dc.photoalbum.model.spring.FileService#getFilesByTagPaging(int,
     *      String, int, int)
     */
    public List<File> getFilesByTagPaging(int userId, String tag,
            int first, int count) {
        return fileDao.getFilesByTagPaging(userId, tag, first, count);
    }

    @Override
    public List<File> getFiles(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getFiles(String orderBy, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getFiles(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }
}
