package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumDao;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformationDao;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.FileDao;
import es.udc.fi.dc.photoalbum.hibernate.FileShareInformationDao;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislikeDao;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

@Transactional
public class FileServiceImpl implements FileService {
	
	/*LikeAndDislike*/
	private LikeAndDislikeDao likeAndDislikeDao;
	
	public LikeAndDislikeDao getLikeAndDislikeDao() {
		return this.likeAndDislikeDao;
	}

	public void setLikeAndDislikeDao(LikeAndDislikeDao likeAndDislikeDao) {
		this.likeAndDislikeDao = likeAndDislikeDao;
	}
	
	// FILE DAO

	private FileDao fileDao;

	public FileDao getFileDao() {
		return this.fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}
	
	// ALBUM DAO

	private AlbumDao albumDao;

	public AlbumDao getAlbumDao() {
		return this.albumDao;
	}

	public void setAlbumDao(AlbumDao albumDao) {
		this.albumDao = albumDao;
	}

	// ALBUM_SHARE_INFORMATION DAO

	private AlbumShareInformationDao albumShareInformationDao;

	public AlbumShareInformationDao getAlbumShareInformationDao() {
		return this.albumShareInformationDao;
	}

	public void setAlbumShareInformationDao(AlbumShareInformationDao albumShareInformationDao) {
		this.albumShareInformationDao = albumShareInformationDao;
	}

	// FILE_SHARE_INFORMATION DAO

	private FileShareInformationDao fileShareInformationDao;

	public FileShareInformationDao getFileShareInformationDao() {
		return this.fileShareInformationDao;
	}

	public void setFileShareInformationDao(FileShareInformationDao fileShareInformationDao) {
		this.fileShareInformationDao = fileShareInformationDao;
	}
	
	// IMPLEMENTATION

	public void create(File file) {
		LikeAndDislike likeAndDislike = new LikeAndDislike();
		likeAndDislikeDao.create(likeAndDislike);
		file.setLikeAndDislike(likeAndDislike);
		fileDao.create(file);
	}

	public void delete(File file) {
		LikeAndDislike lad = file.getLikeAndDislike();
		fileDao.delete(file);
		likeAndDislikeDao.delete(lad);
	}

	public File getFileOwn(int id, String name, int userId) {
		return fileDao.getFileOwn(id, name, userId);
	}

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
			return (fileShareInformationDao.getShare(fileId, userId) == null)
					? null : file;
		}

		// the file inherit its share information from the album
		// => check AlbumShareInformation
		if (filePrivacyLevel.equals(PrivacyLevel.INHERIT_FROM_ALBUM)) {
			int albumId = file.getAlbum().getId();
			return (albumShareInformationDao.getShare(albumId, userId) == null)
					? null : file;
		}

		// unknown privacy level => return nothing
		return null;
	}

	public void changeAlbum(File file, Album album) {
		fileDao.changeAlbum(file, album);
	}

	public File getById(Integer id) {
		return fileDao.getById(id);
	}

	public ArrayList<File> getAlbumFilesOwn(int albumId) {
		return fileDao.getAlbumFilesOwn(albumId);
	}

	public ArrayList<File> getAlbumFilesOwnPaging(int albumId, int first, int count) {
		return fileDao.getAlbumFilesOwnPaging(albumId, first, count);
	}

	public Long getCountAlbumFiles(int albumId) {
		return fileDao.getCountAlbumFiles(albumId);
	}

	public void changePrivacyLevel(File file, String privacyLevel) {
		fileDao.changePrivacyLevel(file, privacyLevel);
		if (privacyLevel.equals(PrivacyLevel.INHERIT_FROM_ALBUM))
			fileShareInformationDao.deleteShares(file.getId());
	}

	public File getFilePublic(int id, String name, int userId) {
		// try to get it as owner
		File file = fileDao.getFileOwn(id, name, userId);
		if (file == null)
			// try to get it as "shared with me" or "public file"
			file = getFileShared(id, name, userId);
		return file;
	}

	public ArrayList<File> getAlbumFilesShared(int albumId, int userId) {
		return fileDao.getAlbumFilesShared(albumId, userId);
	}

	public ArrayList<File> getAlbumFilesSharedPaging(int albumId, int userId,
			int first, int count) {
		return fileDao.getAlbumFilesSharedPaging(albumId, userId, first, count);
	}

	public ArrayList<File> getAlbumFilesPublic(int albumId, int userId) {
		Album album = albumDao.getById(albumId);
		if (album == null)
			return null;
		if (album.getUser().getId() == userId) {
			// I'm the owner, show all the files of the album
			return getAlbumFilesOwn(albumId);
		} else {
			// I'm not the owner, show all public and files shared with me
			return getAlbumFilesShared(albumId, userId);
		}
	}

	public ArrayList<File> getAlbumFilesPublicPaging(int albumId, int userId,
			int first, int count) {
		Album album = albumDao.getById(albumId);
		if (album == null)
			return null;
		if (album.getUser().getId() == userId) {
			// I'm the owner, show all the files of the album
			return getAlbumFilesOwnPaging(albumId, first, count);
		} else {
			// I'm not the owner, show all public and files shared with me
			return getAlbumFilesSharedPaging(albumId, userId, first,
					count);
		}
	}

	public ArrayList<File> getFilesByTag(int userId, String tag) {
		return fileDao.getFilesByTag(userId, tag);
	}

	public ArrayList<File> getFilesByTagPaging(int userId, String tag,
			int first, int count) {
		return fileDao.getFilesByTagPaging(userId, tag, first, count);
	}
}
