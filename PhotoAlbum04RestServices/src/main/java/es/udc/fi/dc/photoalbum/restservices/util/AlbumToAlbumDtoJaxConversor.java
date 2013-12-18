package es.udc.fi.dc.photoalbum.restservices.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.restservices.dto.AlbumDtoJax;

/**
 * Utility class that allows to convert an {@link Album} in an
 * {@link AlbumDtoJax}.
 */
public final class AlbumToAlbumDtoJaxConversor {
    /**
     * Private constructor.
     */
    private AlbumToAlbumDtoJaxConversor(){
    }
    
    /**
     * Method to convert a list of {@link Album} in a list of
     * {@link AlbumDtoJax}.
     * 
     * @param albums
     *            The list which want to convert.
     * @return List<AlbumDtoJax> The converted list.
     */
    public static List<AlbumDtoJax> toAlbumDto(List<Album> albums) {
        List<AlbumDtoJax> albumDtos = new ArrayList<>(albums.size());
        for (Album album : albums) {
            albumDtos.add(toAlbumDto(album));
        }
        return albumDtos;
    }

    /**
     * Method to convert an {@link Album} in an {@link AlbumDtoJax}.
     * 
     * @param album
     *            The album which want to convert.
     * @return AlbumDtoJax The converted album.
     */
    public static AlbumDtoJax toAlbumDto(Album album) {
        return new AlbumDtoJax(album.getId(), album.getName());
    }
}
