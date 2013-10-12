package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface FileShareInformationDao extends GenericDao<FileShareInformation> {

	/**
	 * Lista de usuarios con los que se ha compartido un archivo.
	 */
	ArrayList<FileShareInformation> getFileShares(int fileId);
}
