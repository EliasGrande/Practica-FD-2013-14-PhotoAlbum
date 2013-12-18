package es.udc.fi.dc.photoalbum.webapp.restclient.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.webapp.restclient.dto.FileDtoJax;

/**
 * Utility class that allows to convert an {@link FileDtoJax} in an
 * {@link FileDto}.
 */
public final class FileDtoJaxToFileDtoConversor {
    /**
     * Private constructor.
     */
    private FileDtoJaxToFileDtoConversor() {
    }

    /**
     * Method to convert a list of {@link FileDtoJax} in a list of
     * {@link FileDto}.
     * 
     * @param files
     *            The list which want to convert.
     * @return List<FileDto> The converted list.
     */
    public static List<FileDto> toFileDto(List<FileDtoJax> files) {
        if (files == null) {
            return new ArrayList<FileDto>();
        }
        List<FileDto> fileDtos = new ArrayList<FileDto>(files.size());
        for (FileDtoJax file : files) {
            fileDtos.add(toFileDto(file));
        }
        return fileDtos;
    }

    /**
     * Method to convert an {@link FileDtoJax} in an {@link FileDto}.
     * 
     * @param file
     *            The file which want to convert.
     * @return FileDto The converted file.
     */
    public static FileDto toFileDto(FileDtoJax file) {
        return new FileDto(file.getId(), file.getName(),
                file.getFileSmall());
    }
}
