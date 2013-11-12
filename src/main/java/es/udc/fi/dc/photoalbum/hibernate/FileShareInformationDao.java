package es.udc.fi.dc.photoalbum.hibernate;

import java.util.List;

public interface FileShareInformationDao extends
        GenericDao<FileShareInformation> {

    /**
     * Lista de usuarios con los que se ha compartido un archivo.
     */
    List<FileShareInformation> getFileShares(int fileId);

    /**
     * Recupera un objeto FileShareInformation concreto.
     */
    FileShareInformation getShare(int fileId, int userId);

    /**
     * Elimina todas las tuplas referidas a un fichero.
     */
    void deleteShares(int fileId);
}
