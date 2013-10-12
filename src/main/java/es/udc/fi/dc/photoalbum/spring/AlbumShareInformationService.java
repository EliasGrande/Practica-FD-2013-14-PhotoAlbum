package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;

public interface AlbumShareInformationService {

	void create(AlbumShareInformation shareInformation);

	void delete(AlbumShareInformation shareInformation);

	AlbumShareInformation getShare(String albumName, int userSharedToId,
			String userSharedEmail);

	ArrayList<AlbumShareInformation> getAlbumShares(int albumId);
}
