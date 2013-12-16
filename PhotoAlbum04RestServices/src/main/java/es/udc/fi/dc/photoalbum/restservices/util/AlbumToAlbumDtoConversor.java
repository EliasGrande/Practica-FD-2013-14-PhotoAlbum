package es.udc.fi.dc.photoalbum.restservices.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.restservices.dto.AlbumDtoJax;

public class AlbumToAlbumDtoConversor {

    public static List<AlbumDtoJax> toAlbumDto(List<Album> albums) {
        List<AlbumDtoJax> albumDtos = new ArrayList<>(albums.size());
        for (Album album : albums) {
            albumDtos.add(toAlbumDto(album));
        }
        return albumDtos;
    }

    public static AlbumDtoJax toAlbumDto(Album album) {
        return new AlbumDtoJax(album.getId(), album.getName());
    }
}
