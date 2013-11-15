package es.udc.fi.dc.photoalbum.utils;

import java.util.Random;

/**
 * Utility for creating random string
 */
public final class RandomString {

    /**
     * Variable that for convenience, has been placed statically and
     * whose lifetime extends throughout the execution of the program.
     */
    private static final int LENGTH = 10;
    /**
     * Variable that for convenience, has been placed statically and
     * whose lifetime extends throughout the execution of the program.
     */
    private static final String CHARACTERS = "1234567890abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Constructor for the utility of generating random strings.
     */
    private RandomString() {
    }

    /**
     * Method for generate a random string.
     * 
     * @return random string
     */
    public static String generate() {
        Random rng = new Random();
        char[] text = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            text[i] = CHARACTERS.charAt(rng.nextInt(CHARACTERS
                    .length()));
        }
        return new String(text);
    }
}
