package es.udc.fi.dc.photoalbum.util.utils;

import java.util.Comparator;

/**
 * Comparator for sorting entities that implements the {@link ComparableById} interface.
 */
public class ComparatorById implements Comparator<ComparableById> {

    @Override
    public int compare(ComparableById o1, ComparableById o2) {
        return o1.getId() - o2.getId();
    }

}
