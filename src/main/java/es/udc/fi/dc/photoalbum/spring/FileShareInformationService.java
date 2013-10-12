package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.FileShareInformation;

public interface FileShareInformationService {

	void create(FileShareInformation shareInformation);

	void delete(FileShareInformation shareInformation);

	ArrayList<FileShareInformation> getFileShares(int fileId);
}
