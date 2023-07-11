import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 * @author Rahul Gupta, Alex Marcoux, Problem Set 6
 */
public class Polyline implements Shape {
	// TODO: YOUR CODE HERE
	private final List<Segment> segments; // List of points in the polyline
	private Color color; // Color of the polyline

	public Polyline(Point p, Color color) {
		segments = new ArrayList<>();
		this.color = color;
		segments.add(new Segment(p.x, p.y, color));
	}

	public Polyline(List<Segment> segments, Color color) {
		this.segments = segments;
		this.color = color;
	}

	public void addSegment(int x1, int y1, int x2, int y2) {
		segments.add(new Segment(x1, y1, x2, y2, color));
	}

	@Override
	public void moveBy(int dx, int dy) {
		for (Segment segment : segments) {
			segment.moveBy(dx, dy);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		for (Segment segment : segments) {
			segment.setColor(color);
		}
		this.color = color;
	}

	public Point getCoords() {
		int x1 = Integer.parseInt(segments.get(segments.size() - 1).toString().split(" ")[3]);
		int y1 = Integer.parseInt(segments.get(segments.size() - 1).toString().split(" ")[4]);
		return new Point(x1, y1);
	}

	@Override
	public boolean contains(int x, int y) {
		for (Segment segment : segments) {
			if (segment.contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		for (Segment segment : segments) {
			segment.draw(g);
		}
	}

	// Got help from chatGPT
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("polyline ");
		for (Segment segment : segments) {
			String[] values = segment.toString().split(" ");
			String coords = String.format("%s:%s:%s:%s,", values[1], values[2], values[3], values[4]);
			sb.append(coords);
		}
		sb.append(" ").append(color.getRGB());
		return sb.toString();
	}
}
