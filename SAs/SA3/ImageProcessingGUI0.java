package SA3;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 * Load an image and display it
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Spring 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 * @author Rahul Gupta, Spring 2023, modifies an image locally where the mouse is, as you move the mouse
 */
public class ImageProcessingGUI0 extends DrawingGUI
{
    private int radius = 5;				// how big the drawing effects are
    private ImageProcessor0 proc;		// handles the image processing
    private char action = 'a';			// how to interpret mouse press
    private boolean brush_down;
    private Color pickedColor = Color.white;	// from 'g' action mouse press, starts off as white

    /**
     * Creates the GUI for the image processor, with the window scaled to the to-process image's size
     */
    public ImageProcessingGUI0(ImageProcessor0 proc)
    {
        super("Image processing", proc.getImage().getWidth(), proc.getImage().getHeight());
        this.proc = proc;
    }

    /**
     * DrawingGUI method, here showing the current image
     */
    @Override
    public void draw(Graphics g)
    {
        g.drawImage(proc.getImage(), 0, 0, null);

    }

    /**
     * DrawingGUI method, here dispatching on image processing operations
     */
    @Override
    public void handleKeyPress(char op)
    {
        System.out.println("Handling key '"+op+"'");
        if (op=='s')
        { // save a snapshot
            saveImage(proc.getImage(), "pictures/snapshot.png", "png");
        }
        else if (op=='d')
        {
            // Erasing image
            int red = 255;
            int green = 255;
            int blue = 255;
            // Set the new color of the pixel
            pickedColor = new Color(red, green, blue);
            System.out.println("picked color "+pickedColor);
            brush_down = true;
            action = op;
        }
        else if (op == 'u')
        {
            brush_down = false;
        }
        else if (op == 'c')
        {
            action = op;
        }
        else
        {
            System.out.println("Unknown operation");
        }
        repaint(); // Re-draw, since image has changed
    }


    @Override
    public void handleMouseMotion(int x, int y)
    {
        if (brush_down)
        {
            // Erase the image at the location of the mouse pointer
            if (action == 'd')
            {
                proc.erase(x, y, radius, pickedColor);
            }
        }

        // Invert the pixels at the location of the mouse pointer
        else if (action == 'c')
        {
            proc.ChangePixels(x, y, radius);
        }

        repaint(); // Re-draw everything, since image has changed
    }

    public static void main(String[] args)
        {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                // Load the image to process
                BufferedImage baker = loadImage("pictures/baker.jpg");
                // Create a new processor, and a GUI to handle it
                new ImageProcessingGUI0(new ImageProcessor0(baker));
            }
        });
    }
}





