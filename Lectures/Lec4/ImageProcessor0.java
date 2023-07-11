package Lec4;

import java.awt.image.BufferedImage;

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Spring 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 */
public class ImageProcessor0 {
    private BufferedImage image;		// the current image being processed

    /**
     * @param image		the original
     */
    public ImageProcessor0(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}

