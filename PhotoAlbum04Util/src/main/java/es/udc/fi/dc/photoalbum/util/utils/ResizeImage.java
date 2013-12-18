package es.udc.fi.dc.photoalbum.util.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

/**
 * Utility for resizing images
 */
public final class ResizeImage {

    /**
     * Constructor for the utility of resizing.
     */
    private ResizeImage() {
    }

    /**
     * Resizes images
     * 
     * @param image
     *            image for resizing
     * @param size
     *            resized image max height and width
     * 
     * @param contentType
     *            Describe the content of a file
     * @return resized image
     */
    public static byte[] resize(byte[] image, int size,
            String contentType) {
        try {
            InputStream in = new ByteArrayInputStream(image);
            BufferedImage buffImage = ImageIO.read(in);
            if (!((buffImage.getWidth() < size) && (buffImage
                    .getHeight() < size))) {
                buffImage = Scalr.resize(buffImage, size);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (("image/png").equals(contentType)) {
                ImageIO.write(buffImage, "png", baos);
            } else {
                ImageIO.write(buffImage, "jpg", baos);
            }

            baos.flush();
            byte[] resizedInByte = baos.toByteArray();
            baos.close();
            return resizedInByte;
        } catch (IOException e) {
            return null;
        }
    }
}
