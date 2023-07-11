package SA3;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Spring 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 */
public class ImageProcessor0
{
    private BufferedImage image;		// the current image being processed

    /**
     * @param image		the original
     */
    public ImageProcessor0(BufferedImage image)
    {
        this.image = image;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    /**
     * Returns a value that is one of val (if it's between min or max) or min or max (if it's outside that range).
     * @param val
     * @param min
     * @param max
     * @return constrained value
     */
    public static double constrain(double val, double min, double max)
    {
        if (val < min)
        {
            return min;
        }
        else if (val > max)
        {
            return max;
        }
        return val;
    }

    /**
     * Returns an image that's the same size & type as the current, but blank
     */
    private BufferedImage createBlankResult()
    {
        // Create a new image into which the resulting pixels will be stored.
        return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Returns an image that's a copy of the current one
     */
    private BufferedImage createCopyResult()
    {
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
    }

    /**
     * Erases the colors within a square at the location
     *
     * @param cx	  center x of square
     * @param cy	  center y of square
     * @param r   radius of square
     * @param color  fill
     */
    public void erase(int cx, int cy, int r, Color color)
    {
        // Nested loop over nearby pixels
        for (int y = Math.max(0, cy - r); y < Math.min(image.getHeight(), cy + r + 1); y++)
        {
            for (int x = Math.max(0, cx-r); x < Math.min(image.getWidth(), cx+r+1); x++)
            {
                image.setRGB(x, y, color.getRGB());
            }
        }
    }
    /**
     * Inverts the pixels within a square at the location
     *
     * @param cx	  center x of square
     * @param cy	  center y of square
     * @param r   radius of square
     */
    public void ChangePixels(int cx, int cy, int r)
    {
        // Nested loop over nearby pixels
        for (int y = Math.max(0, cy - r); y < Math.min(image.getHeight(), cy + r + 1); y++)
        {
            for (int x = Math.max(0, cx - r); x < Math.min(image.getWidth(), cx + r + 1); x++)
            {
                // Get the color of the current pixel
                Color c = new Color(image.getRGB(x, y));
                // Invert the color
                int red = 255 - c.getRed();
                int green = 255 - c.getGreen();
                int blue = 255 - c.getBlue();
                // Set the new color of the pixel
                Color newColor = new Color(red, green, blue);
                image.setRGB(x, y, newColor.getRGB());
            }
        }
    }

}




