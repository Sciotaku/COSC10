public class Vertex {
    int id;
    int dist;
    Vertex pred;

    public Vertex(int id) {
        this.id = id;
        this.dist = Integer.MAX_VALUE;
        this.pred = null;
    }
}