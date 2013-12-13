package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.utils.RandomString;

/**
 * The class <code>RandomStringTest</code> contains tests for the
 * class {@link <code>RandomString</code>}.
 */
public class RandomStringTest {

    private static final int GENERATE_COUNT = 100;
    private static final int MAX_REPEATED_COUNT = (GENERATE_COUNT * 5) / 100; // error = 5%

    /**
     * Run the String generate() method test
     */
    @Test
    public void testGenerate() {
        ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0; i < GENERATE_COUNT; i++) {
            strings.add(RandomString.generate());
        }
        Collections.sort(strings);
        String previous = null;
        int repeated = 0;
        for (String current : strings) {
            if (current.equals(previous)) {
                repeated++;
            }
            previous = current;
        }
        assertTrue(repeated <= MAX_REPEATED_COUNT);
    }
}