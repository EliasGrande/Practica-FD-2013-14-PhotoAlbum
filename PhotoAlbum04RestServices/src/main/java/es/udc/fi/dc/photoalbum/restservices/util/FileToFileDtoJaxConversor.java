package es.udc.fi.dc.photoalbum.restservices.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.restservices.dto.FileDtoJax;

/**
 * Utility class that allows to convert an {@link File} in an
 * {@link FileDtoJax}.
 */
public final class FileToFileDtoJaxConversor {
    /**
     * Private constructor.
     */
    private FileToFileDtoJaxConversor() {

    }

    /**
     * Method to convert a list of {@link File} in a list of
     * {@link FileDtoJax}.
     * 
     * @param files
     *            The list which want to convert.
     * @return List<FileDtoJax> The converted list.
     */
    public static List<FileDtoJax> toFileDto(List<File> files) {
        List<FileDtoJax> fileDtos = new ArrayList<>(files.size());
        for (File file : files) {
            fileDtos.add(toFileDto(file));
        }
        return fileDtos;
    }

    /**
     * Method to convert an {@link File} in an {@link FileDtoJax}.
     * 
     * @param file
     *            The file which want to convert.
     * @return FileDtoJax The converted file.
     */
    public static FileDtoJax toFileDto(File file) {
        return new FileDtoJax(file.getId(), file.getName(),
                file.getFileSmall());
    }
}
