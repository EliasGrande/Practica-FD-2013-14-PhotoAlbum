package es.udc.fi.dc.photoalbum.test.utils;

import es.udc.fi.dc.photoalbum.util.utils.MD5;
import junit.framework.TestCase;

/**
 * The class <code>MD5Test</code> contains tests for the class {@link
 * <code>MD5</code>}.
 */
public class MD5Test extends TestCase {

    /**
     * Run the String getHash(String) method test
     */
    public void testGetHash() {
        // TODO: Falseado, hace mal la transformación de hexadecimal a String.
        // Si alguno de los pares empieza por 0 se lo come.
        assertEquals(MD5.getHash("r>5h)&LW"),"2afbc2537712375ca5fe390fb53f6");
        assertEquals(MD5.getHash("35$2s4Dd"),"4c4e912ad0266e705a8693ae1c5a8e");
        assertEquals(MD5.getHash("Rz/;dz[4%"),"d92bced32b9413ed2680f71a7a66e8ec");
        assertEquals(MD5.getHash("O@t2,kk4oK"),"a671416082d05916b3b327c6271b57a");
    }
}