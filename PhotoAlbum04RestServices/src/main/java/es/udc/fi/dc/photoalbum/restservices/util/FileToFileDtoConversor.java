package es.udc.fi.dc.photoalbum.restservices.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.restservices.dto.FileDtoJax;

public class FileToFileDtoConversor {

    public static List<FileDtoJax> toFileDto(List<File> files) {
        List<FileDtoJax> fileDtos = new ArrayList<>(files.size());
        for (File file : files) {
            fileDtos.add(toFileDto(file));
        }
        return fileDtos;
    }

    public static FileDtoJax toFileDto(File file) {
        return new FileDtoJax(file.getId(), file.getName(),
                file.getFileSmall());
    }
}
