package PS2;
import java.util.ArrayList;
import java.util.List;

/**
 * A point quadtree: stores an element at a 2D position,
 * with children at the subdivided quadrants.
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015.
 * @author CBK, Spring 2016, explicit rectangle.
 * @author CBK, Fall 2016, generic with Point2D interface.
 * @author Rahul Gupta, Spring 2023, Problem Set 2
 * @author Alexander Marcoux, Spring 2023, Problem Set 2
 */
public class PointQuadtree<E extends Point2D> {
	private E point;                            // the point anchoring this node
	private int x1, y1;                            // upper-left corner of the region
	public int tree_size = 0;
	private int x2, y2;                            // bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;    // children

	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	// Getters

	public E getPoint() {
		return point;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 *
	 * @param quadrant 1 through 4
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant == 1) return c1;
		if (quadrant == 2) return c2;
		if (quadrant == 3) return c3;
		if (quadrant == 4) return c4;
		return null;
	}

	/**
	 * Returns whether or not there is a child at the given quadrant, 1-4
	 *
	 * @param quadrant 1 through 4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant == 1 && c1 != null) || (quadrant == 2 && c2 != null) || (quadrant == 3 && c3 != null) || (quadrant == 4 && c4 != null);
	}

	/**
	 * Inserts the point into the tree
	 */
	public void insert(E p2) {
		// TODO: YOUR CODE HERE
		int x = (int) point.getX();	// calls getter method
		int y = (int) point.getY();
		// now checking for correct quadrant
		if (p2.getX() >= point.getX()) {
			// <= y will be first quadrant
			if (p2.getY() <= point.getY()) {
				// First quadrant
				if (hasChild(1)) {
					getChild(1).insert(p2);
				}
				else {
					// if it doesn't have a child, create new quad tree
					c1 = new PointQuadtree<E>(p2, x, getY1(), getX2(), y);
				}
			}
			// >= y will be fourth quadrant
			else {
				// Fourth quadrant
				if (hasChild(4)) {
					getChild(4).insert(p2);
				}
				else {
					// if it doesn't have a child, create new quad tree
					c4 = new PointQuadtree<E>(p2, x, y, getX2(), getY2());
				}
			}
		}
		else {
			// <= x for third and second quadrant
			// <= y for second quadrant
			if (p2.getY() <= point.getY()) {
				// Second quadrant
				if (hasChild(2)) {
					getChild(2).insert(p2);
				}
				else {
					// if it doesn't have a child, create new quad tree
					c2 = new PointQuadtree<E>(p2, getX1(), getY1(), x, y);
				}
			}
			// >= y for third quadrant
			else {
				// Third quadrant
				if (hasChild(3)) {
					getChild(3).insert(p2);
				}
				else {
					// if it doesn't have a child, create new quad tree
					c3 = new PointQuadtree<E>(p2, getX1(), y, x, getY2());
				}
			}
		}
	}

	/**
	 * Finds the number of points in the quadtree (including its descendants)
	 */
	public int size() {
		// TODO: YOUR CODE HERE
		int numOfPoints = 1;
		for (int i = 1; i <= 4; i++) {
			if (hasChild(i)) {
				// recursively calls on size of child granted the child exists
				numOfPoints += getChild(i).size();
			}
		}
		return numOfPoints;
	}

	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 */
	public List<E> allPoints() {
		// TODO: YOUR CODE HERE
		// instantiating new array list of points
		List<E> points = new ArrayList<E>();
		points.add(point);
		for(int i = 1; i <= 4; i++) {
			if(hasChild(i)) {
				// recursive call to add all children to array list
				points.addAll(getChild(i).allPoints());
			}
		}
		// returns list after all points have been added
		return points;
	}

	/**
	 * Uses the quadtree to find all points within the circle
	 *
	 * @param cx circle center x
	 * @param cy circle center y
	 * @param cr circle radius
	 * @return the points in the circle (and the qt's rectangle)
	 */
	// Got help from Aamish Ahmad Beg, '26.
	public List<E> findInCircle(double cx, double cy, double cr) {
		// new array list to store points found
		List<E> pointsFound = new ArrayList<E>();
		// calls on recursive helper to add to list
		findInCircleRecursively(this, cx, cy, cr, pointsFound);
		// returns list once all found points have been added
		return pointsFound;
	}

	private void findInCircleRecursively(PointQuadtree<E> tree, double cx, double cy, double cr, List<E> pointsFound) {
		// if tree is empty, no points will be found
		if (tree == null) {
			return;
		}

		// code from geometry class
		if (!Geometry.circleIntersectsRectangle(cx, cy, cr, tree.getX1(), tree.getY1(), tree.getX2(), tree.getY2())) {
			return;
		}

		// if point is in circle, add point to found list
		if (Geometry.pointInCircle(tree.getPoint().getX(), tree.getPoint().getY(), cx, cy, cr)) {
			pointsFound.add(tree.getPoint());
		}

		// call method on all children
		for (int i = 1; i <= 4; i++) {
			findInCircleRecursively(tree.getChild(i), cx, cy, cr, pointsFound);
		}
	}
}








