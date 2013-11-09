package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface AlbumShareInformationDao extends
        GenericDao<AlbumShareInformation> {

    /**
     * Lista de usuarios con los que se ha compartido un álbum.
     */
    ArrayList<AlbumShareInformation> getAlbumShares(int albumId);

    /**
     * Recupera un objeto AlbumShareInformation concreto.
     */
    AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail);

    /**
     * Recupera un objeto AlbumShareInformation concreto.
     */
    AlbumShareInformation getShare(int albumId, int userId);
}
