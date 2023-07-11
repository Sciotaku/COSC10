package pset1;
import java.awt.*;
import java.awt.image.*;
import java.sql.SQLOutput;
import java.util.*;

/**
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 *
 * @author Chris Bailey-Kellogg, Spring 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Spring 2015
 * @author CBK, Spring 2015, updated for CamPaint
 * @author Rahul Gupta, Spring 2023, Problem Set 1
 * @author Alexander Marcoux, Spring 2023, Problem Set 1
 */
public class RegionFinder
{
	// instance variables
	private static final int maxColorDiff = 20;                // how similar a pixel color must be to the target color, to belong to a region
	private static final int minRegion = 50;                 // how many points in a region to be worth considering
	private BufferedImage image;                            // the image in which to find regions
	private BufferedImage recoloredImage;                   // the image with identified regions recolored
	private ArrayList<ArrayList<Point>> regions;            // a region is a list of points, so the identified regions are in a list of lists of points

	// constructors
	public RegionFinder()
	{
		this.image = null;
	}

	public RegionFinder(BufferedImage image)
	{
		this.image = image;
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public BufferedImage getRecoloredImage()
	{
		return recoloredImage;
	}

	/**
	 * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
	 */
	public void findRegions(Color targetColor) {
		// Initialize the visited image with all black pixels (not visited)
		BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		regions = new ArrayList<>();

		// Loop over all pixels in the image
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				// Check if the pixel hasn't been visited yet and is of the correct color
				if (visited.getRGB(x, y) == 0 && colorMatch(new Color(image.getRGB(x, y)), targetColor)) {
					// Start a new region and add the current pixel to it
					ArrayList<Point> region = new ArrayList<>();
					region.add(new Point(x, y));
					// Keep track of which pixels need to be visited, initially just the current pixel
					ArrayList<Point> toVisit = new ArrayList<>();
					toVisit.add(new Point(x, y));

					// As long as there's some pixel that needs to be visited
					while (toVisit.size() != 0) {
						// get one to visit
						Point p = toVisit.remove(0);
						// add it to the region
						region.add(p);
						// mark it as visited
						visited.setRGB(p.x, p.y, 1);
						// loop over all its neighbors
						for (int my = -1; my <= 1; my++) {
							for (int mx = -1; mx <= 1; mx++) {
								// don't visit itself or diagonal neighbors
								if (mx == 0 && my == 0 || Math.abs(mx) == Math.abs(my))
									continue;
								// calculating the coordinates of the neighbor pixel
								int nx = p.x + mx;
								int ny = p.y + my;
								// checking if the neighbor pixel is within the image bounds, hasn't been visited yet,
								// and is of the correct color
								if (nx >= 0 && nx < image.getWidth() && ny >= 0 && ny < image.getHeight()
										&& visited.getRGB(nx, ny) == 0 && colorMatch(new Color(image.getRGB(nx, ny)), targetColor)) {
									// adding neighbor pixel to the toVisit list
									toVisit.add(new Point(nx, ny));
									// adding neighbor pixel to current region
									region.add(new Point(nx, ny));
									// marking the neighbor pixels as visited
									visited.setRGB(nx, ny, 1);
								}
							}
						}
					}

					// if the region is big enough, it is stored
					if (region.size() > minRegion)
					{
						regions.add(region);
					}
				}
			}
		}
	}

	/**
	 * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
	 */
	private static boolean colorMatch(Color c1, Color c2) {

//		int redDiff = c1.getRed() - c2.getRed();
//		int greenDiff = c1.getGreen() - c2.getGreen();
//		int blueDiff = c1.getBlue() - c2.getBlue();
//		double colorDiff = Math.sqrt(redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff);
//		return colorDiff <= maxColorDiff;
//		seeking feedback on the code above: error message received

		return Math.abs((c1.getRed() - c2.getRed())) <= maxColorDiff
				&& Math.abs((c1.getGreen() - c2.getGreen())) <= maxColorDiff
				&& Math.abs((c1.getBlue() - c2.getBlue())) <= maxColorDiff;
	}

	/**
	 * Returns the largest region detected (if any region has been detected)
	 */
	public ArrayList<Point> largestRegion() {
		// TODO: YOUR CODE HERE
		if (regions.size() == 0) {
			return null;
		}
		ArrayList<Point> maxRegion = new ArrayList<>();
		for (ArrayList<Point> region : regions) {
			if (region.size() > maxRegion.size()) {
				maxRegion = region;
			}
		}
		return maxRegion;

//		seeking feedback on the code below: error message received
//		ArrayList<Point> largest = new ArrayList<Point>();
//		for(ArrayList<Point> region : regions) {
//			if(region.size() > largest.size()) {
//				largest = region;
//			}
//		}
//		return largest;
	}


	/**
	 * Sets recoloredImage to be a copy of image,
	 * but with each region a uniform random color,
	 * so we can see where they are
	 */
	public void recolorImage() {
		// first copy the original
		recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		// recoloring the regions in it
		for (ArrayList<Point> region : regions) {
			// generating a random color for the region
			Color color = new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
			// set the color of all pixels in the region to the generated color
			for (Point point : region) {
				recoloredImage.setRGB(point.x, point.y, color.getRGB());
			}
		}
	}
}








