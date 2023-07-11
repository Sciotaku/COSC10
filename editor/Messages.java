import java.awt.Color;
import java.util.ArrayList;
/**
 * A class for translating String messages received and translating them back into Sketch commands.
 * This class is responsible for decoding messages and modifying the Sketch accordingly.
 * Author: Jeff Kobal, Andrew Wu
 */
public class Messages {

    /**
     * Converts a String message (generated in EditorCommunicator) back to a command or shape.
     *
     * @param sketch Sketch to edit using this function
     * @param line   Message to interpret
     * @return Input sketch after making edits from the message
     */
    public static Sketch Message(Sketch sketch, String line) {
        String[] p = line.split(" ");

        switch (p[0]) {
            case "add":
                switch (p[1]) {
                    case "ellipse": {
                        int x = Integer.parseInt(p[2]);
                        int y = Integer.parseInt(p[3]);
                        int width = Integer.parseInt(p[4]);
                        int height = Integer.parseInt(p[5]);
                        Color color = new Color(Integer.parseInt(p[6]));
                        Ellipse rShape = new Ellipse(x, y, width, height, color);
                        sketch.addShape(rShape);
                        break;
                    }
                    case "rectangle": {
                        int x = Integer.parseInt(p[2]);
                        int y = Integer.parseInt(p[3]);
                        int width = Integer.parseInt(p[4]);
                        int height = Integer.parseInt(p[5]);
                        Color color = new Color(Integer.parseInt(p[6]));
                        Rectangle rShape = new Rectangle(x, y, width, height, color);
                        sketch.addShape(rShape);
                        break;
                    }
                    case "segment": {
                        int x1 = Integer.parseInt(p[2]);
                        int y1 = Integer.parseInt(p[3]);
                        int x2 = Integer.parseInt(p[4]);
                        int y2 = Integer.parseInt(p[5]);
                        Color color = new Color(Integer.parseInt(p[6]));
                        Segment rShape = new Segment(x1, y1, x2, y2, color);
                        sketch.addShape(rShape);
                        break;
                    }
                    case "polyline": {
                        Color color = new Color(Integer.parseInt(p[p.length - 1]));
                        String[] coords = p[2].split(",");
                        ArrayList<Segment> segments = new ArrayList<>();
                        for (String coord : coords) {
                            String[] segCoords = coord.split(":");
                            int x1 = Integer.parseInt(segCoords[0]);
                            int y1 = Integer.parseInt(segCoords[1]);
                            int x2 = Integer.parseInt(segCoords[2]);
                            int y2 = Integer.parseInt(segCoords[3]);
                            segments.add(new Segment(x1, y1, x2, y2, color));
                        }
                        Polyline rShape = new Polyline(segments, color);
                        sketch.addShape(rShape);
                        break;
                    }
                }
                break;
            case "delete": {
                int shapeIndex = Integer.parseInt(p[1]);
                sketch.removeShape(shapeIndex);
                break;
            }
            case "move": {
                int shapeIndex = Integer.parseInt(p[1]);
                int dx = Integer.parseInt(p[2]);
                int dy = Integer.parseInt(p[3]);
                Shape s = sketch.getShape(shapeIndex);
                s.moveBy(dx, dy);
                sketch.setShape(shapeIndex, s);
                break;
            }
            case "recolor": {
                int shapeIndex = Integer.parseInt(p[1]);
                int colorValue = Integer.parseInt(p[2]);
                Color color = new Color(colorValue);
                sketch.getShape(shapeIndex).setColor(color);
                break;
            }
        }

        return sketch;
    }
}
