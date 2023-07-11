import java.util.*;

public class Dijkstra{
    List<Vertex> vertices;
    List<Edge> edges;

    public Dijkstra(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public void relax(Vertex u, Vertex v, int weight) {
        if (u.dist != Integer.MAX_VALUE && u.dist + weight < v.dist) {
            v.dist = u.dist + weight;
            v.pred = u;
        }
    }

    public void dijkstra(Vertex source) {
        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingInt(v -> v.dist));
        for (Vertex v : vertices) {
            v.dist = Integer.MAX_VALUE;
            v.pred = null;
            queue.add(v);
        }

        source.dist = 0;

        while (!queue.isEmpty()) {
            Vertex u = queue.poll();

            for (Edge e : edges) {
                if (e.source == u) {
                    relax(e.source, e.destination, e.weight);
                }
            }
        }
    }

    public List<Vertex> getShortestPath(Vertex destination) {
        List<Vertex> path = new ArrayList<>();
        Vertex current = destination;

        while (current != null) {
            path.add(current);
            current = current.pred;
        }

        Collections.reverse(path);
        return path;
    }

    public void printShortestPath(Vertex destination) {
        List<Vertex> shortestPath = getShortestPath(destination);

        System.out.print("Shortest path: ");
        for (int i = 0; i < shortestPath.size(); i++) {
            System.out.print(shortestPath.get(i).id);
            if (i != shortestPath.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Create vertices
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);
        Vertex v5 = new Vertex(5);

        // Create edges
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(v1, v2, 2));
        edges.add(new Edge(v1, v3, 4));
        edges.add(new Edge(v2, v3, 1));
        edges.add(new Edge(v2, v4, 7));
        edges.add(new Edge(v3, v4, 3));
        edges.add(new Edge(v3, v5, 5));
        edges.add(new Edge(v4, v5, 2));

        List<Vertex> vertices = Arrays.asList(v1, v2, v3, v4, v5);
        Dijkstra algorithm = new Dijkstra(vertices, edges);
        algorithm.dijkstra(v1);

        // Get the shortest path from vertex 1 to vertex 5
        List<Vertex> shortestPath = algorithm.getShortestPath(v5);

        // Print the shortest path
        for (Vertex vertex : shortestPath) {
            System.out.println("Vertex: " + vertex.id + ", Distance: " + vertex.dist);
        }
        // Print the shortest path from vertex 1 to vertex 5
        algorithm.printShortestPath(v5);
    }
}
