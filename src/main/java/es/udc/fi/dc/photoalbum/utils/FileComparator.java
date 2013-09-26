package es.udc.fi.dc.photoalbum.utils;

import java.util.Comparator;

import es.udc.fi.dc.photoalbum.hibernate.File;

/**
 * Defines comparator for Files, comparing by id
 */
public class FileComparator implements Comparator<File> {

	public int compare(File file1, File file2) {
		if (file1.getId() > file2.getId()) {
			return 1;
		} else {
			return -1;
		}
	}
}
