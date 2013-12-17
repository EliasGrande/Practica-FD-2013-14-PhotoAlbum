package es.udc.fi.dc.photoalbum.webapp.restclient.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.webapp.restclient.dto.FileDtoJax;

public class FileDtoJaxToFileDtoConversor {

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

    public static FileDto toFileDto(FileDtoJax file) {
        return new FileDto(file.getId(), file.getName(),
                file.getFileSmall());
    }
}
