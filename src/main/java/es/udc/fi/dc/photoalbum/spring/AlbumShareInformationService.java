package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;

public interface AlbumShareInformationService {

    void create(AlbumShareInformation shareInformation);

    void delete(AlbumShareInformation shareInformation);

    AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail);

    List<AlbumShareInformation> getAlbumShares(int albumId);
}
