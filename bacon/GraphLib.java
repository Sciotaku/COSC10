import java.util.*;

/**
 * Graph library class with BFS-related methods.
 * @author Rahul Gupta, Dartmouth Class of 2026
 * @author Alexander Marcoux, Dartmouth Class of 2026
 * Got help from GraphLib class from lecture notes.
 */

public class GraphLib {

    // BFS to find shortest path tree for a current center of the universe. Return a path tree as a Graph.
    public static <V, E> Graph<V, E> bfs(Graph<V, E> g, V source) {
        // BFS Algorithm
        // enqueue(s) //start node
        // s.visited = true
        // repeat until find goal vertex or
        // queue empty:
        //    u = dequeque()
        //    (do something here)
        //    for v ∈ u.adjacent
        //        if !v.visited
        //        v.visited = true
        //        enqueue(v)
        Graph<V, E> pathTree = new AdjacencyMapGraph<>();
        Queue<V> queue = new LinkedList<>();
        Set<V> visited = new HashSet<V>();

        pathTree.insertVertex(source);
        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            V u = queue.remove();
            for (V v : g.outNeighbors(u)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                    pathTree.insertVertex(v);
                    pathTree.insertDirected(v, u, g.getLabel(v, u));
                }
            }
        }
        return pathTree;
    }

    // Given a shortest path tree and a vertex, construct a path from the vertex back to the center of the universe.
    public static <V, E> List<V> getPath(Graph<V, E> tree, V v) {
        List<V> path = new ArrayList<V>();
        path.add(v);
        while (tree.outDegree(v) > 0) {
            v = tree.outNeighbors(v).iterator().next();
            path.add(0, v);
        }
        return path;
    }

    // Given a graph and a subgraph (here shortest path tree), determine which vertices are in the graph but not the
    // subgraph (here, not reached by BFS).
    public static <V, E> Set<V> missingVertices(Graph<V, E> graph, Graph<V, E> subGraph) {
        Set<V> missVert = new HashSet<>();
        for (V v : graph.vertices()) {
            if (!subGraph.hasVertex(v)) {
                missVert.add(v);
            }
        }
        return missVert;
    }

    // Find the average distance-from-root in a shortest path tree. Note: do this without enumerating all the paths!
    // Hint: think tree recursion...
    public static <V, E> double averageSeparation(Graph<V, E> tree, V root) {
        double totalSeparation = calculateSeparation(tree, root, 0);
        int totalNodes = tree.numVertices() - 1;
        return totalSeparation / totalNodes;
    }

    private static <V, E> double calculateSeparation(Graph<V, E> tree, V node, double depth) {
        if (tree.outDegree(node) == 0) { // if node is a leaf, return the depth
            return depth;
        }

        for (V child : tree.outNeighbors(node)) {
            depth += calculateSeparation(tree, child, depth + 1);
        }

        return depth;
    }

    public static void main(String[] args) throws Exception {
        // Test Case
        Graph<String, String> test = new AdjacencyMapGraph<>();

        // add the vertices
        test.insertVertex("Kevin Bacon");
        test.insertVertex("Alice");
        test.insertVertex("Bob");
        test.insertVertex("Charlie");
        test.insertVertex("Nobody");
        test.insertVertex("Nobody's Friend");
        test.insertVertex("Dartmouth");

        // add the edges
        test.insertUndirected("Kevin Bacon", "Alice", "A Movie, E Movie");
        test.insertUndirected("Kevin Bacon", "Bob", "A Movie");
        test.insertUndirected("Bob", "Alice", "A Movie");
        test.insertUndirected("Alice", "Charlie", "D Movie");
        test.insertUndirected("Bob", "Charlie", "C Movie");
        test.insertUndirected("Charlie", "Dartmouth", "B Movie");
        test.insertUndirected("Nobody", "Nobody's Friend", "F Movie");

        // find the shortest path tree
        Graph<String, String> tree = bfs(test, "Kevin Bacon");

        // construct a path from a vertex back to the root
        List<String> path = getPath(tree, "Charlie");

        System.out.println("Path from Kevin Bacon to Charlie:");
        for (String vertex : path) {
            System.out.println(vertex);
        }

        // find the missing vertices
        Set<String> missing = missingVertices(test, tree);
        System.out.println("Vertices missing from shortest path tree:");
        for (String vertex : missing) {
            System.out.println(vertex);
        }

        // calculate the average separation from the root
        double average = averageSeparation(tree, "Bob");
        System.out.println("Average separation from Kevin Bacon: " + average);
    }
}
