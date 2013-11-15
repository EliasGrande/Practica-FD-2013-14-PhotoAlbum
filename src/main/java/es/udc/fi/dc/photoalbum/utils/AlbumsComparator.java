package es.udc.fi.dc.photoalbum.utils;

import java.util.Comparator;

import es.udc.fi.dc.photoalbum.hibernate.Album;

/**
 * Defines comparator for Albums, comparing by id
 */
public class AlbumsComparator implements Comparator<Album> {

    /**
     * Compare two albums comparing by album id.
     * 
     * @param album1
     *            First {@link Album} for the compare.
     * @param album2
     *            Second {@link Album} for the compare.
     * @return If the first one album identifier is higher than the
     *         second album identifier returns 1, else -1.
     */
    public int compare(Album album1, Album album2) {
        if (album1.getId() > album2.getId()) {
            return 1;
        } else {
            return -1;
        }
    }
}
