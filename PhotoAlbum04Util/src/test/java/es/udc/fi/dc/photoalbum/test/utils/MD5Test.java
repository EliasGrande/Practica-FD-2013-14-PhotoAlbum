package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.utils.MD5;

/**
 * The class <code>MD5Test</code> contains tests for the class {@link <code>MD5</code>}
 * .
 */
public class MD5Test {

    /**
     * Run the String getHash(String) method test
     */
    @Test
    public void testGetHash() {
        assertEquals(MD5.getHash("dasdasdas"),
                "63373b41cf913e9f9b3226b4a0452737");
        assertEquals(MD5.getHash("r>5h)&LW"),
                "02afbc25377102375ca5fe3900fb53f6");
        assertEquals(MD5.getHash("Rz/;dz[4%"),
                "d92bced32b9413ed2680f71a7a66e8ec");
        assertEquals(
                MD5.getHash("ot*8Z%]<+$/p@zx.;[/C1!-6HMz]>,z_/q=-]G^.Q<?G[!\"-+(G;N|&!<2\"2~K{! q('d6>,63e^<)])[s8UXEd~.@'VK!Z+3$Sn"),
                "263cc18c9d7775631f6c2178f9c80c6c");
    }
}