package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.FileShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.FileShareInformationDao;

/**
 * Implementation of the {@link FileShareInformationSservice}.
 */
@Transactional
public class FileShareInformationServiceImpl implements
        FileShareInformationService {
    /**
     * @see FileShareInformationDao
     */
    private FileShareInformationDao fileShareInformationDao;

    /**
     * Method for get a FileShareInformationDao.
     * 
     * @return FileShareInformationDao
     */
    public FileShareInformationDao getFileShareInformationDao() {
        return this.fileShareInformationDao;
    }

    /**
     * Method for put a FileShareInformationDao.
     * 
     * @param fileShareInformationDao
     *            {@link FileShareInformationDao} that will be put.
     */
    public void setFileShareInformationDao(
            FileShareInformationDao fileShareInformationDao) {
        this.fileShareInformationDao = fileShareInformationDao;
    }

    /**
     * Allows to create an {@link FileShareInformation}
     * 
     * @param shareInformation
     *            {@link FileShareInformation } that will be created.
     * @see es.udc.fi.dc.photoalbum.spring.FileShareInformationService#create(FileShareInformation)
     */
    public void create(FileShareInformation shareInformation) {
        fileShareInformationDao.create(shareInformation);
    }

    /**
     * Allows to remove an {@link FileShareInformation}
     * 
     * @param shareInformation
     *            {@link FileShareInformation } that will be removed.
     * @see es.udc.fi.dc.photoalbum.spring.FileShareInformationService#delete(FileShareInformation)
     */
    public void delete(FileShareInformation shareInformation) {
        fileShareInformationDao.delete(shareInformation);
    }

    /**
     * Return a list of {@link FileShareInformation } searching by
     * identifier
     * 
     * @param fileId
     *            A {@link File} indentifier.
     * @return A list of {@link FileShareInformation} of a
     *         {@link File}.
     * @see es.udc.fi.dc.photoalbum.spring.FileShareInformationService#getFileShares(int)
     */
    public List<FileShareInformation> getFileShares(int fileId) {
        return fileShareInformationDao.getFileShares(fileId);
    }
}
