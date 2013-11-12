package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.FileShareInformation;

public interface FileShareInformationService {

    void create(FileShareInformation shareInformation);

    void delete(FileShareInformation shareInformation);

    List<FileShareInformation> getFileShares(int fileId);
}
