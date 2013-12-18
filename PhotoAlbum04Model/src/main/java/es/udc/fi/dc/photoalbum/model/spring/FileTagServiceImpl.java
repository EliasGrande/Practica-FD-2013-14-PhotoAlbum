package es.udc.fi.dc.photoalbum.model.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.model.hibernate.FileTagDao;

/**
 * Implementation of the {@link FileTagService}
 */
public class FileTagServiceImpl implements FileTagService {

    /**
     * @see FileTagDao
     */
    private FileTagDao fileTagDao;

    /**
     * Method for get an {@link FileTagDao}.
     * 
     * @return An {@link FileTagDao}.
     */
    public FileTagDao getFileTagDao() {
        return this.fileTagDao;
    }

    /**
     * Method that allows to put an {@link FileTagDao}.
     * 
     * @param fileTagDao
     *            {@link FileTagDao} which will be put.
     */
    public void setFileTagDao(FileTagDao fileTagDao) {
        this.fileTagDao = fileTagDao;
    }

    /**
     * Method that allows to create an {@link FileTag}.
     * 
     * @param fileTag
     *            {@link FileTag} that will be created.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileTagService#create(FileTag)
     */
    public void create(FileTag fileTag) {
        if (fileTagDao.getTag(fileTag.getFile().getId(),
                fileTag.getTag()) == null) {
            fileTagDao.create(fileTag);
        }
    }

    /**
     * Method that allows to delete an {@link FileTag}.
     * 
     * @param fileTag
     *            {@link FileTag} that will be deleted.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileTagService#delete(FileTag)
     */
    public void delete(FileTag fileTag) {
        fileTagDao.delete(fileTag);

    }

    /**
     * Method for get an {@link FileTag} searching by {@link File}
     * identifier and a tag name.
     * 
     * @param fileId
     *            {@link File} identifier.
     * @param tag
     *            {@link FileTag} tag.
     * @return {@link FileTag} object that corresponds with that
     *         {@link File} identifier and tag.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileTagService#getTag(int,
     *      String)
     */
    public FileTag getTag(int fileId, String tag) {
        return fileTagDao.getTag(fileId, tag);
    }

    /**
     * Get a list of {@link FileTag} searching by {@link File}
     * identifier.
     * 
     * @param fileId
     *            An {@link File} identifier.
     * @return A list of {@link FileTag} whose {@link File} identifier
     *         is the {@link File} id.
     * @see es.udc.fi.dc.photoalbum.model.spring.FileTagService#getTags(int)
     */
    public List<FileTag> getTags(int fileId) {
        return fileTagDao.getTags(fileId);
    }

}
