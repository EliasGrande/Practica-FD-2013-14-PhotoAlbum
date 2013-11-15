package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.FileShareInformation;

/**
 * Interface for the {@link FileShareInformationSservice}.
 */
public interface FileShareInformationService {

    /**
     * Allows to create an {@link FileShareInformation}
     * 
     * @param shareInformation
     *            {@link FileShareInformation} that will be created.
     */
    void create(FileShareInformation shareInformation);

    /**
     * Allows to remove an {@link FileShareInformation}
     * 
     * @param shareInformation
     *            {@link FileShareInformation} that will be removed.
     */
    void delete(FileShareInformation shareInformation);

    /**
     * Return a list of {@link FileShareInformation } searching by
     * identifier
     * 
     * @param fileId
     *            A {@link File} indentifier.
     * @return A list of {@link FileShareInformation} of a {@link File}.
     */
    List<FileShareInformation> getFileShares(int fileId);
}
