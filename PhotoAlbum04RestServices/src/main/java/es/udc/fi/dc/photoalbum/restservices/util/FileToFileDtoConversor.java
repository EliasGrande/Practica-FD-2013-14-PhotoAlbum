package es.udc.fi.dc.photoalbum.restservices.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.util.dto.FileDto;

public class FileToFileDtoConversor {

    public static List<FileDto> toFileDto(List<File> files) {
        List<FileDto> fileDtos = new ArrayList<>(files.size());
        for (File file : files) {
            fileDtos.add(toFileDto(file));
        }
        return fileDtos;
    }

    public static FileDto toFileDto(File file) {
        return new FileDto(file.getId(), file.getName(),
                file.getFileSmall());
    }
}
