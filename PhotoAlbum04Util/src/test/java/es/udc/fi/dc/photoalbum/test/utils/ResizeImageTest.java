package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.sun.mail.util.BASE64DecoderStream;

import es.udc.fi.dc.photoalbum.util.utils.ResizeImage;

/**
 * The class <code>ResizeImageTest</code> contains tests for the class
 * {@link <code>ResizeImage</code>}.
 */
public class ResizeImageTest {

    private static final int ORIGINAL_WIDTH = 200;
    private static final int ORIGINAL_HEIGHT = 100;
    
    private static final int RESIZED_WIDTH = 100;
    private static final int RESIZED_HEIGHT = 50;
    private static final int SIZE = Math.max(RESIZED_WIDTH,
            RESIZED_HEIGHT);
    
    private static final String CONTENT_TYPE_PNG = "image/png";
    private static final byte[] IMAGE_PNG = BASE64DecoderStream
            .decode(new String(
                    "iVBORw0KGgoAAAANSUhEUgAAAMgAAABkCAIAAABM5OhcAAAAyElEQVR42u3SMREAAAjEMMC/58cE"
                            + "A0MioddOUnBtJMBYGAtjgbEwFsYCY2EsjAXGwlgYC4yFsTAWGAtjYSwwFsbCWGAsjIWxwFgYC2OB"
                            + "sTAWxgJjYSyMBcbCWBgLjIWxMBYYC2NhLDAWxsJYYCyMhbHAWBgLY4GxMBbGAmNhLIwFxsJYGAuM"
                            + "hbEwFhgLY2EsMBbGwlhgLIyFscBYGAtjgbEwFsYCY2EsjAXGwlgYC4yFsTAWxgJjYSyMBcbCWBgL"
                            + "jIWxMBYYi98WU5QDxbEy+mYAAAAASUVORK5CYII=")
                    .getBytes());
    
    private static final String CONTENT_TYPE_JPG = "image/jpeg";
    private static final byte[] IMAGE_JPG = BASE64DecoderStream
            .decode(new String(
                    "/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAP//////////////////////////////////////////"
                            + "////////////////////////////////////////////2wBDAf//////////////////////////"
                            + "////////////////////////////////////////////////////////////wAARCABkAMgDAREA"
                            + "AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA"
                            + "AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3"
                            + "ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm"
                            + "p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA"
                            + "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx"
                            + "BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK"
                            + "U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3"
                            + "uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwCSgAoA"
                            + "KACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAo"
                            + "AKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgA"
                            + "oAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACg"
                            + "AoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKAC"
                            + "gAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKA"
                            + "CgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAK"
                            + "ACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoA"
                            + "KACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAo"
                            + "AKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgA"
                            + "oAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgD//2Q==")
                    .getBytes());

    /**
     * Run the byte[] resize(byte[], int, String) method test for PNG
     * images
     */
    @Test
    public void testResizePNG() throws IOException {
        // verify original image size
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(
                IMAGE_PNG));
        assertEquals(image.getWidth(), ORIGINAL_WIDTH);
        assertEquals(image.getHeight(), ORIGINAL_HEIGHT);
        // verify resized image size
        byte[] resizedImageBytes = ResizeImage.resize(IMAGE_PNG,
                SIZE, CONTENT_TYPE_PNG);
        image = ImageIO.read(new ByteArrayInputStream(
                resizedImageBytes));
        assertEquals(image.getWidth(), RESIZED_WIDTH);
        assertEquals(image.getHeight(), RESIZED_HEIGHT);
    }

    /**
     * Run the byte[] resize(byte[], int, String) method test for JPG
     * images
     */
    @Test
    public void testResizeJPG() throws IOException {
        // verify original image size
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(
                IMAGE_JPG));
        assertEquals(image.getWidth(), ORIGINAL_WIDTH);
        assertEquals(image.getHeight(), ORIGINAL_HEIGHT);
        // verify resized image size
        byte[] resizedImageBytes = ResizeImage.resize(IMAGE_JPG,
                SIZE, CONTENT_TYPE_JPG);
        image = ImageIO.read(new ByteArrayInputStream(
                resizedImageBytes));
        assertEquals(image.getWidth(), RESIZED_WIDTH);
        assertEquals(image.getHeight(), RESIZED_HEIGHT);
    }
}