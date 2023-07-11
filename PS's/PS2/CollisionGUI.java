package PS2;
import java.awt.*;

import javax.swing.*;

import java.sql.Array;
import java.util.List;
import java.util.ArrayList;

/**
 * Using a quadtree for collision detection
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Spring 2016, updated for blobs
 * @author CBK, Fall 2016, using generic PointQuadtree
 * @author Rahul Gupta, Spring 2023, Problem Set 2
 * @author Alexander Marcoux, Spring 2023, Problem Set 2
 */
public class CollisionGUI extends DrawingGUI {
	private static final int width=800, height=600;		// size of the universe

	private List<Blob> blobs;						// all the blobs
	private List<Blob> colliders;					// the blobs who collided at this step
	private char blobType = 'b';						// what type of blob to create
	private char collisionHandler = 'c';				// when there's a collision, 'c'olor them, or 'd'estroy them
	private int delay = 100;							// timer control

	public CollisionGUI() {
		super("super-collider", width, height);

		blobs = new ArrayList<>();

		// Timer drives the animation.
		startTimer();
	}

	/**
	 * Adds an blob of the current blobType at the location
	 */
	private void add(int x, int y) {
		if (blobType=='b') {
			blobs.add(new Bouncer(x,y,width,height));
		}
		else if (blobType=='w') {
			blobs.add(new Wanderer(x,y));
		}
		else {
			System.err.println("Unknown blob type "+blobType);
		}
	}

	/**
	 * DrawingGUI method, here creating a new blob
	 */
	public void handleMousePress(int x, int y) {
		add(x,y);
		repaint();
	}

	/**
	 * DrawingGUI method
	 */
	public void handleKeyPress(char k) {
		if (k == 'f') { // faster
			if (delay>1) delay /= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 's') { // slower
			delay *= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 'r') { // add some new blobs at random positions
			for (int i=0; i<10; i++) {
				add((int)(width*Math.random()), (int)(height*Math.random()));
				repaint();
			}
		}
		else if (k == 'c' || k == 'd') { // control how collisions are handled
			collisionHandler = k;
			System.out.println("collision:"+k);
		}
		else { // set the type for new blobs
			blobType = k;
		}
	}

	/**
	 * DrawingGUI method, here drawing all the blobs and then re-drawing the colliders in red
	 */
	public void draw(Graphics g) {
		// TODO: YOUR CODE HERE
		// Draw all the blobs
		for(Blob blob : blobs) {
			blob.draw(g);
		}
		// Draw the colliding blobs in red
		if(colliders != null) {
			g.setColor(Color.RED);
			// Draw the colliders
			for(Blob collider : colliders) {
				collider.draw(g);
			}
		}
	}

	/**
	 * Sets colliders to include all blobs in contact with another blob
	 */
	// Got help from Aamish Ahmad Beg, '26.
	private void findColliders() {
		// TODO: YOUR CODE HERE
		// Check if there are no blobs
		if(blobs.isEmpty()) {
			return;
		}

		// making a new quad tree with first blob
		PointQuadtree<Blob> tree = new PointQuadtree<Blob>(blobs.get(0), 0, 0, width, height);

		// add all blobs
		for(int i = 1; i < blobs.size(); i++) {
			tree.insert(blobs.get(i));
		}
		// new array list for colliders
		colliders = new ArrayList<Blob>();
		// check for collisions across all blobs
		for(Blob blob: blobs) {
			List<Blob> blobsCollided = tree.findInCircle(blob.getX(), blob.getY(), blob.getR() * 2);
			blobsCollided.remove(blob);
			blobsCollided.removeAll(colliders);
			// removes duplicate blobs from tree and inserts into array list
			colliders.addAll(blobsCollided);
		}
	}

	/**
	 * DrawingGUI method, here moving all the blobs and checking for collisions
	 */
	public void handleTimer() {
		// Ask all the blobs to move themselves.
		for (Blob blob : blobs) {
			blob.step();
		}
		// Check for collisions
		if (blobs.size() > 0) {
			findColliders();
			if (collisionHandler=='d') {
				blobs.removeAll(colliders);
				colliders = null;
			}
		}
		// Now update the drawing
		repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CollisionGUI();
			}
		});
	}
}














