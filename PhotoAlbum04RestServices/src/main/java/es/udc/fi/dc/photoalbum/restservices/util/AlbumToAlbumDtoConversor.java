package es.udc.fi.dc.photoalbum.restservices.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.util.dto.AlbumDto;

public class AlbumToAlbumDtoConversor {

    public static List<AlbumDto> toAlbumDto(List<Album> albums) {
        List<AlbumDto> albumDtos = new ArrayList<>(albums.size());
        for (Album album : albums) {
            albumDtos.add(toAlbumDto(album));
        }
        return albumDtos;
    }

    public static AlbumDto toAlbumDto(Album album) {
        return new AlbumDto(album.getId(), album.getName());
    }
}
