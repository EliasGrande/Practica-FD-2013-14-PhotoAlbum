package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformationDao;

/**
 * Implementation of the {@link AlbumShareInformationService}
 */
@Transactional
public class AlbumShareInformationServiceImpl implements
        AlbumShareInformationService {

    /**
     * @see AlbumShareInformationDao
     */
    private AlbumShareInformationDao albumShareInformationDao;

    /**
     * Method getAlbumShareInformationDao. Get an instance of
     * AlbumShareInformationDao
     * 
     * @return A {@link AlbumShareInformationDao}
     */
    public AlbumShareInformationDao getAlbumShareInformationDao() {
        return this.albumShareInformationDao;
    }

    /**
     * Set an instance of {@link AlbumShareInformationDao}.
     * 
     * @param shareInformationDao
     *            {@link AlbumShareInformationDao} that will be
     *            instanced
     */
    public void setAlbumShareInformationDao(
            AlbumShareInformationDao shareInformationDao) {
        this.albumShareInformationDao = shareInformationDao;
    }

    /**
     * Allows to create an {@link AlbumShareInformation}
     * 
     * @param shareInformation
     *            {@link AlbumShareInformation } that will be created.
     * 
     * @see es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService
     *      #create(AlbumShareInformation)
     */
    public void create(AlbumShareInformation shareInformation) {
        albumShareInformationDao.create(shareInformation);
    }

    /**
     * Allows to remove an {@link AlbumShareInformation}
     * 
     * @param shareInformation
     *            {@link AlbumShareInformation } that will be removed.
     * 
     * @see es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService
     *      #delete(AlbumShareInformation)
     */
    public void delete(AlbumShareInformation shareInformation) {
        albumShareInformationDao.delete(shareInformation);
    }

    /**
     * Return an {@link AlbumShareInformation} searching by album
     * name, user target identifier and user owner email.
     * 
     * @param albumName
     *            The {@link Album} name.
     * @param userSharedToId
     *            The target user
     * @param userSharedEmail
     *            The email of the user who shares the {@link Album}.
     * 
     * @return {@link AlbumShareInformation} for that search. * @see
     *         es.udc.fi.dc.photoalbum.spring.
     *         AlbumShareInformationService #getShare(String,
     *         int,String)
     */
    public AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail) {
        return albumShareInformationDao.getShare(albumName,
                userSharedToId, userSharedEmail);
    }

    /**
     * Get a list of {@link AlbumShareInformation} from an
     * {@link Album} searching by album identifier
     * 
     * @param albumId
     *            An {@link Album} identifier.
     * 
     * @return list of {@link AlbumShareInformation} * @see
     *         es.udc.fi.dc
     *         .photoalbum.spring.AlbumShareInformationService
     *         #getAlbumShares(int)
     */
    public List<AlbumShareInformation> getAlbumShares(int albumId) {
        return albumShareInformationDao.getAlbumShares(albumId);
    }
}
