package es.udc.fi.dc.photoalbum.util.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * For getting MD5 hash
 */
public final class MD5 {

    /**
     * Constructor for the MD5 hash utility.
     */
    private MD5() {
    }

    /**
     * Defines a conversion hexadecimal-string.
     */
    private static final int HEX_TO_STRING = 0xFF;

    /**
     * @param str
     *            String which will be used for do the hash function.
     * 
     * @return md5 hash
     */
    public static String getHash(String str) {
        MessageDigest md5;
        StringBuilder hexString = new StringBuilder();
        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
            md5.update(str.getBytes());
            byte[] messageDigest = md5.digest();
            for (byte aMessageDigest : messageDigest) {
                String hexStr = Integer.toHexString(HEX_TO_STRING
                        & aMessageDigest);
                hexStr = (hexStr.length() > 1) ? hexStr : "0"
                        + hexStr;
                hexString.append(hexStr);
            }
        } catch (NoSuchAlgorithmException e) {
            return e.toString();
        }
        return hexString.toString();
    }
}
