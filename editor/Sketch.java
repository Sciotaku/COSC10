import java.awt.*;
import java.util.*;

/**
 * @author Rahul Gupta, Alex Marcoux, Problem Set 6
 */
public class Sketch {
    private final Map<Integer, Shape> shapes; // Mapping of shape id to shape object
    private int nextId; // Next available id for a new shape

    public Sketch() {
        shapes = new TreeMap<>();
        nextId = 0;
    }

    public synchronized void addShape(Shape shape) {
        shapes.put(nextId, shape);
        nextId++;
    }

    public synchronized void setShape(int id, Shape shape) {
        shapes.put(id, shape);
    }

    public synchronized void removeShape(int id) {
        shapes.remove(id);
    }

    public synchronized Shape getShape(int id) {
        return shapes.get(id);
    }

    public synchronized Integer getID(Point p) {
        for (Map.Entry<Integer, Shape> entry : shapes.entrySet()) {
            int id = entry.getKey();
            Shape shape = entry.getValue();
            if (shape.contains((int) p.getX(), (int) p.getY())) {
                return id;
            }
        }
        return null;
    }

    public synchronized Map<Integer, Shape> getShapes() {
        return shapes;
    }

    public synchronized void draw(Graphics g) {
        for (Shape shape : shapes.values()) {
            shape.draw(g);
        }
    }

    public synchronized void empty() {
        shapes.clear();
        nextId = 0;
    }
}
