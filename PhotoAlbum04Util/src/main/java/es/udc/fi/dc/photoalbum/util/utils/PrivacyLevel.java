package es.udc.fi.dc.photoalbum.util.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Utility for the privacy level of albums and files.
 */
public final class PrivacyLevel {

    /**
     * Static variable to facilitate the use of string privacy level
     */
    public static final String PRIVATE = "PRIVATE";
    /**
     * Static variable to facilitate the use of string privacy level
     */
    public static final String PUBLIC = "PUBLIC";
    /**
     * Static variable to facilitate the use of string privacy level
     */
    public static final String INHERIT_FROM_ALBUM = "INHERIT_FROM_ALBUM";

    /**
     * List of possible values ​​of level of privacy for albums.
     */
    public static final List<String> LIST_ALBUM = Arrays
            .asList(new String[] { PRIVATE, PUBLIC });

    /**
     * List of possible values ​​of level of privacy for files.
     */
    public static final List<String> LIST_FILE = Arrays
            .asList(new String[] { PRIVATE, PUBLIC,
                    INHERIT_FROM_ALBUM });

    /**
     * Check that the value indicated is privacy among potential for
     * an album. Useful in form validation.
     * 
     * @param privacyLevel
     *            Privacy level to validate.
     * @return True if is valid, false otherwise.
     */
    public static boolean validateAlbum(String privacyLevel) {
        return LIST_ALBUM.contains(privacyLevel);
    }

    /**
     * Check that the value indicated is privacy among potential for a
     * file. Useful in form validation.
     * 
     * @param privacyLevel
     *            Privacy level to validate.
     * @return True if is valid, false otherwise.
     */
    public static boolean validateFile(String privacyLevel) {
        return LIST_FILE.contains(privacyLevel);
    }

    /**
     * Private constructor.
     */
    private PrivacyLevel() {

    }
}