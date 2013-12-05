package es.udc.fi.dc.photoalbum.webapp.wicket;

import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import es.udc.fi.dc.photoalbum.model.hibernate.File;

/**
 * Gets Blob from File.
 * 
 */
public final class BlobFromFile {

    /**
     * Private constructor of BlobFromFile.
     */
    private BlobFromFile() {
    }

    /**
     * @param file
     *            {@link File}, small image from what you want to get.
     * 
     * @return Blob of small {@link File}.
     */
    public static Blob getSmall(File file) {
        try {
            return new SerialBlob(file.getFileSmall());
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * @param file
     *            {@link File}, big image from what you want to get.
     * 
     * @return Blob of big {@link File}.
     */
    public static Blob getBig(File file) {
        try {
            return new SerialBlob(file.getFile());
        } catch (SQLException e) {
            return null;
        }
    }
}
