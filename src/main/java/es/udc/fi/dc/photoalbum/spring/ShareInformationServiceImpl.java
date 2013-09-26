package es.udc.fi.dc.photoalbum.spring;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.ShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.ShareInformationDao;
import es.udc.fi.dc.photoalbum.hibernate.User;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class ShareInformationServiceImpl implements ShareInformationService {

	private ShareInformationDao shareInformationDao;

	public ShareInformationDao getShareInformationDao() {
		return this.shareInformationDao;
	}

	public void setShareInformationDao(ShareInformationDao shareInformationDao) {
		this.shareInformationDao = shareInformationDao;
	}

	public void create(ShareInformation shareInformation) {
		shareInformationDao.create(shareInformation);
	}

	public void delete(ShareInformation shareInformation) {
		shareInformationDao.delete(shareInformation);
	}

	public List<ShareInformation> getShares(User userShared, User userSharedTo) {
		return shareInformationDao.getShares(userShared, userSharedTo);
	}

	public ShareInformation getShare(String albumName, int userSharedToId,
			String userSharedEmail) {
		return shareInformationDao.getShare(albumName, userSharedToId,
				userSharedEmail);
	}

	public ArrayList<ShareInformation> getAlbumShares(int albumId) {
		return shareInformationDao.getAlbumShares(albumId);
	}

	public ArrayList<ShareInformation> getUserShares(int userId) {
		return shareInformationDao.getUserShares(userId);
	}
}
