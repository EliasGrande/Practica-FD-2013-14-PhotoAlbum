package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformationDao;

/**
 * Implementation of the AlbumShareInformation service
 */
@Transactional
public class AlbumShareInformationServiceImpl implements
        AlbumShareInformationService {

    private AlbumShareInformationDao albumShareInformationDao;

    /**
     * Method getAlbumShareInformationDao. Get an instance of
     * AlbumShareInformationDao
     * 
     * @return AlbumShareInformationDao The AlbumShareInformationDao
     */
    public AlbumShareInformationDao getAlbumShareInformationDao() {
        return this.albumShareInformationDao;
    }

    /**
     * Method setAlbumShareInformationDao. Set an instance of
     * AlbumShareInformationDao.
     * 
     * @param shareInformationDao
     *            AlbumShareInformationDao The
     *            AlbumShareInformationDao that will be instanced
     */
    public void setAlbumShareInformationDao(
            AlbumShareInformationDao shareInformationDao) {
        this.albumShareInformationDao = shareInformationDao;
    }

    /**
     * Method create. Create an AlbumShareInformation
     * 
     * @param shareInformation
     *            AlbumShareInformation The album share information
     *            that will be created
     * @see es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService
     *      #create(AlbumShareInformation)
     */
    public void create(AlbumShareInformation shareInformation) {
        albumShareInformationDao.create(shareInformation);
    }

    /**
     * Method delete. Delete an AlbumShareInformation
     * 
     * @param shareInformation
     *            AlbumShareInformation The album share information
     *            that will be removed
     * @see es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService
     *      #delete(AlbumShareInformation)
     */
    public void delete(AlbumShareInformation shareInformation) {
        albumShareInformationDao.delete(shareInformation);
    }

    /**
     * Method getShare. Get an album share information searching by
     * album name, user target identifier and user owner email.
     * 
     * @param albumName
     *            String The album name
     * @param userSharedToId
     *            int The target user
     * @param userSharedEmail
     *            String The email of the user who shares the album.
     * @return AlbumShareInformation The album share information for
     *         that search.
     * @see es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService
     *      #getShare(String, int, String)
     */
    public AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail) {
        return albumShareInformationDao.getShare(albumName,
                userSharedToId, userSharedEmail);
    }

    /**
     * Method getAlbumShares. Get a list of album share information
     * from an album searching by album identifier.
     * 
     * @param albumId
     *            int The album identifier.
     * @return List<AlbumShareInformation> The list of album share
     *         information
     * @see es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService#getAlbumShares(int)
     */
    public List<AlbumShareInformation> getAlbumShares(int albumId) {
        return albumShareInformationDao.getAlbumShares(albumId);
    }
}
