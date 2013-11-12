package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformationDao;

@Transactional
public class AlbumShareInformationServiceImpl implements
        AlbumShareInformationService {

    private AlbumShareInformationDao albumShareInformationDao;

    public AlbumShareInformationDao getAlbumShareInformationDao() {
        return this.albumShareInformationDao;
    }

    public void setAlbumShareInformationDao(
            AlbumShareInformationDao shareInformationDao) {
        this.albumShareInformationDao = shareInformationDao;
    }

    public void create(AlbumShareInformation shareInformation) {
        albumShareInformationDao.create(shareInformation);
    }

    public void delete(AlbumShareInformation shareInformation) {
        albumShareInformationDao.delete(shareInformation);
    }

    public AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail) {
        return albumShareInformationDao.getShare(albumName,
                userSharedToId, userSharedEmail);
    }

    public List<AlbumShareInformation> getAlbumShares(int albumId) {
        return albumShareInformationDao.getAlbumShares(albumId);
    }
}
