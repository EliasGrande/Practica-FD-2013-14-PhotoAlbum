package es.udc.fi.dc.photoalbum.util.utils;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for sorting entities that implements the
 * {@link ComparableById} interface.
 */
@SuppressWarnings("serial")
public class ComparatorById implements Comparator<ComparableById>,
        Serializable {

    @Override
    public int compare(ComparableById o1, ComparableById o2) {
        return o1.getId() - o2.getId();
    }

}
