package SA7;

public class GraphLibTest {
    public static void main(String[] args) {
        Graph<String, String> g = new AdjacencyMapGraph<>();
        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertDirected("A", "B", "AB");
        g.insertDirected("A", "C", "AC");
        g.insertDirected("A", "D", "AD");
        g.insertDirected("A", "E", "AE");
        g.insertDirected("B", "A", "BA");
        g.insertDirected("B", "C", "BC");
        g.insertDirected("C", "A", "CA");
        g.insertDirected("C", "B", "CB");
        g.insertDirected("C", "D", "CD");
        g.insertDirected("E", "B", "EB");
        g.insertDirected("E", "C", "EC");

        // Try some random walks
        System.out.println(GraphLib.randomWalk(g, "A", 0));  // [A]
        System.out.println(GraphLib.randomWalk(g, "A", 1));  // [A, B], [A, C], [A, D], or [A, E]
        System.out.println(GraphLib.randomWalk(g, "A", 2));
        System.out.println(GraphLib.randomWalk(g, "A", 3));
        System.out.println(GraphLib.randomWalk(g, "A", 4));

        // Get vertices sorted by in-degree
        System.out.println(GraphLib.verticesByInDegree(g));
    }
}
