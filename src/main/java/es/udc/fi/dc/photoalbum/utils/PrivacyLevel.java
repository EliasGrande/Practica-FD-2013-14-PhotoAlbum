package es.udc.fi.dc.photoalbum.utils;

import java.util.Arrays;
import java.util.List;

public class PrivacyLevel {

    public static final String PRIVATE = "PRIVATE";
    public static final String PUBLIC = "PUBLIC";
    public static final String INHERIT_FROM_ALBUM = "INHERIT_FROM_ALBUM";

    /**
     * Lista de valores posibles de nivel de privacidad para álbumes.
     */
    public static final List<String> LIST_ALBUM = Arrays
            .asList(new String[] { PRIVATE, PUBLIC });

    /**
     * Lista de valores posibles de nivel de privacidad para archivos.
     */
    public static final List<String> LIST_FILE = Arrays
            .asList(new String[] { PRIVATE, PUBLIC,
                    INHERIT_FROM_ALBUM });

    /**
     * Comprueba que el valor de privacidad indicado está entre los
     * posibles para un álbum. Útil en validacion de formularios.
     */
    public static boolean validateAlbum(String privacyLevel) {
        return LIST_ALBUM.contains(privacyLevel);
    }

    /**
     * Comprueba que el valor de privacidad indicado está entre los
     * posibles para un archivo. Útil en validacion de formularios.
     */
    public static boolean validateFile(String privacyLevel) {
        return LIST_FILE.contains(privacyLevel);
    }
}