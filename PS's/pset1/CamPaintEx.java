package pset1;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Extra-credit file for PS1
 * Changes the color of the region based on user input (see handleKeyPress)
 *
 * @author Alexander Marcoux, Spring 2023, Problem Set 1
 * @author Rahul Gupta, Spring 2023, Problem Set 1
 */
public class CamPaintEx extends Webcam
{
	// instance variables
	private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
	private RegionFinder finder;			// handles the finding
	private Color targetColor;          	// color of regions of interest (set by mouse press)
	private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
	private BufferedImage painting;			// the resulting masterpiece

	/**
	 * Initializes the region finder and the drawing
	 */
	public CamPaintEx()
	{
		finder = new RegionFinder();
		clearPainting();
	}

	/**
	 * Resets the painting to a blank image
	 */
	protected void clearPainting()
	{
		painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * DrawingGUI method, here drawing one of live webcam, recolored image, or painting,
	 * depending on display variable ('w', 'r', or 'p')
	 */
	@Override
	public void draw(Graphics g)
	{
		// TODO: YOUR CODE HERE
		if (displayMode == 'w')
		{
			g.drawImage(super.image, 0, 0, null);
		}
		else if (displayMode == 'p')							// draws new specified image on a white background
		{
			g.drawImage(painting, 0, 0, null);
		}
		else if (displayMode == 'r')
		{
			g.drawImage(finder.getRecoloredImage(), 0, 0, null);
		}
	}

	/**
	 * Webcam method, here finding regions and updating the painting.
	 */
	@Override
	public void processImage()
	{
		// if no target color is specified (if user does not click)
		if(targetColor == null)
			return;
		finder.setImage(super.image);
		finder.findRegions(targetColor);
		finder.recolorImage();

		// paints the largest region acquired from regionsFinder
		ArrayList<Point> largestRegion = finder.largestRegion();
		for(Point point: largestRegion) {
			painting.setRGB(point.x, point.y, paintColor.getRGB());
		}
	}

	/**
	 * Overrides the DrawingGUI method to set the track color.
	 */
	@Override
	public void handleMousePress(int x, int y)
	{
		// uses mouse coordinates to set target color
		clearPainting();
		targetColor = new Color(super.image.getRGB(x, y));
	}

	/**
	 * DrawingGUI method, here doing various drawing commands
	 */
	@Override
	public void handleKeyPress(char k) {
		// using switch instead of if-else syntax
		switch (k) {
			case 'p': case 'k': case 'w':
				// display: painting, recolored image, or webcam
				displayMode = k;
				break;
			case 'c':
				// clear
				clearPainting();
				break;
			case 'o':
				// save the recolored image
				saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
			case 's':
				// save the painting
				saveImage(painting, "pictures/painting.png", "png");
				break;
			default:
				System.out.println("unexpected key " + k);
				break;
			case ' ':
				// hitting spacebar prompts user to give the rgb values; uses those values as a new color
				// local variables
				boolean working = false;
				int parseRed = 0;
				int parseGreen = 0;
				int parseBlue = 0;
				// using bufferedReader to read the input from the user
				while (!working)
				{
					BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
					System.out.println("What are you 3 favorite numbers between 0 and 255 (inclusive; press enter after each number)? ");

					try
					{
						parseRed = Integer.parseInt(input.readLine());
						parseGreen = Integer.parseInt(input.readLine());
						parseBlue = Integer.parseInt(input.readLine());
						working = true;
					}
					// if input is not correct, re-prompts user and relays error messege
					catch (Exception e) {
						System.out.println("Give it another shot!");
					}
				}
				paintColor = new Color(parseRed, parseGreen, parseBlue);
				break;
		}
	}

		public static void main (String[]args){
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new CamPaintEx();
				}
			});
		}
	}






