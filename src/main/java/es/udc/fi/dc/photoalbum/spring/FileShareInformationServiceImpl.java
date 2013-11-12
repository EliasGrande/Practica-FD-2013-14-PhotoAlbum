package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.FileShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.FileShareInformationDao;

@Transactional
public class FileShareInformationServiceImpl implements
        FileShareInformationService {

    private FileShareInformationDao fileShareInformationDao;

    public FileShareInformationDao getFileShareInformationDao() {
        return this.fileShareInformationDao;
    }

    public void setFileShareInformationDao(
            FileShareInformationDao fileShareInformationDao) {
        this.fileShareInformationDao = fileShareInformationDao;
    }

    public void create(FileShareInformation shareInformation) {
        fileShareInformationDao.create(shareInformation);
    }

    public void delete(FileShareInformation shareInformation) {
        fileShareInformationDao.delete(shareInformation);
    }

    public List<FileShareInformation> getFileShares(int fileId) {
        return fileShareInformationDao.getFileShares(fileId);
    }
}
