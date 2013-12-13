package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.util.utils.RandomString;

/**
 * The class <code>PrivacyLevelTest</code> contains tests for the class {@link
 * <code>PrivacyLevel</code>}.
 */
public class PrivacyLevelTest {

    /**
     * Run the boolean validateAlbum(String) method test
     */
    @Test
    public void testValidateAlbum() {
        assertTrue(PrivacyLevel.validateAlbum(PrivacyLevel.PRIVATE));
        assertTrue(PrivacyLevel.validateAlbum(PrivacyLevel.PUBLIC));
        assertFalse(PrivacyLevel.validateAlbum(PrivacyLevel.INHERIT_FROM_ALBUM));
        assertFalse(PrivacyLevel.validateAlbum(PrivacyLevel.PRIVATE+RandomString.generate()));
        assertFalse(PrivacyLevel.validateAlbum(PrivacyLevel.PUBLIC+RandomString.generate()));
        assertFalse(PrivacyLevel.validateAlbum(RandomString.generate()+PrivacyLevel.PRIVATE));
        assertFalse(PrivacyLevel.validateAlbum(RandomString.generate()+PrivacyLevel.PUBLIC));
    }

    /**
     * Run the boolean validateFile(String) method test
     */
    @Test
    public void testValidateFile() {
        assertTrue(PrivacyLevel.validateFile(PrivacyLevel.PRIVATE));
        assertTrue(PrivacyLevel.validateFile(PrivacyLevel.PUBLIC));
        assertTrue(PrivacyLevel.validateFile(PrivacyLevel.INHERIT_FROM_ALBUM));
        assertFalse(PrivacyLevel.validateFile(PrivacyLevel.PRIVATE+RandomString.generate()));
        assertFalse(PrivacyLevel.validateFile(PrivacyLevel.PUBLIC+RandomString.generate()));
        assertFalse(PrivacyLevel.validateFile(PrivacyLevel.INHERIT_FROM_ALBUM+RandomString.generate()));
        assertFalse(PrivacyLevel.validateFile(RandomString.generate()+PrivacyLevel.PRIVATE));
        assertFalse(PrivacyLevel.validateFile(RandomString.generate()+PrivacyLevel.PUBLIC));
        assertFalse(PrivacyLevel.validateFile(RandomString.generate()+PrivacyLevel.INHERIT_FROM_ALBUM));
    }
}