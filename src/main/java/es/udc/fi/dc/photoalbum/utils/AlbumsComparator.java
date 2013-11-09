package es.udc.fi.dc.photoalbum.utils;

import java.util.Comparator;

import es.udc.fi.dc.photoalbum.hibernate.Album;

/**
 * Defines comparator for Albums, comparing by id
 */
public class AlbumsComparator implements Comparator<Album> {

    public int compare(Album album1, Album album2) {
        if (album1.getId() > album2.getId()) {
            return 1;
        } else {
            return -1;
        }
    }
}
