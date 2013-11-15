package es.udc.fi.dc.photoalbum.utils;

import java.util.Comparator;

import es.udc.fi.dc.photoalbum.hibernate.File;

/**
 * Defines comparator for Files, comparing by id
 */
public class FileComparator implements Comparator<File> {

    /**
     * Compare two files comparing by album id.
     * 
     * @param file1
     *            First {@link File} for the compare.
     * @param file2
     *            Second {@link File} for the compare.
     * @return If the first one album identifier is higher than the
     *         second album identifier returns 1, else -1.
     */
    public int compare(File file1, File file2) {
        if (file1.getId() > file2.getId()) {
            return 1;
        } else {
            return -1;
        }
    }
}
