package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.ShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.User;

public interface ShareInformationService {

	void create(ShareInformation shareInformation);

	void delete(ShareInformation shareInformation);

	List<ShareInformation> getShares(User userShared, User userSharedTo);

	ShareInformation getShare(String albumName, int userSharedToId,
			String userSharedEmail);

	ArrayList<ShareInformation> getAlbumShares(int albumId);

	ArrayList<ShareInformation> getUserShares(int userId);
}
