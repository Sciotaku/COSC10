package SA7;
import java.util.*;

/**
 * Library for graph analysis
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * @author Rahul Gupta, Short Assignment 7, Spring 2023
 *
 */
public class GraphLib {
	/**
	 * Takes a random walk from a vertex, up to a given number of steps
	 * So a 0-step path only includes start, while a 1-step path includes start and one of its out-neighbors,
	 * and a 2-step path includes start, an out-neighbor, and one of the out-neighbor's out-neighbors
	 * Stops earlier if no step can be taken (i.e., reach a vertex with no out-edge)
	 * @param g		graph to walk on
	 * @param start	initial vertex (assumed to be in graph)
	 * @param steps	max number of steps
	 * @return		a list of vertices starting with start, each with an edge to the sequentially next in the list;
	 * 			    null if start isn't in graph
	 */
	public static <V,E> List<V> randomWalk(Graph<V,E> g, V start, int steps) {
		// TODO: your code here

        if (!g.hasVertex(start)) {
            return null;
        }
        List<V> path = new ArrayList<V>();
        path.add(start);
        V curr = start;
        for (int i = 0; i < steps; i++) {
            Iterable<V> neighbors = g.outNeighbors(curr);
            List<V> neighborList = new ArrayList<V>();
            for (V neighbor : neighbors) {
                neighborList.add(neighbor);
            }
            if (neighborList.size() == 0) {
                break;
            }
            int randomIndex = (int) (Math.random() * neighborList.size());
            curr = neighborList.get(randomIndex);
            path.add(curr);
        }
        return path;
	}

	/**
	 * Orders vertices in decreasing order by their in-degree
	 * @param g		graph
	 * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
	 */
	public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
		// TODO: your code here

        List<V> vertices = new ArrayList<V>();
        for (V vertex : g.vertices()) {
            vertices.add(vertex);
        }
        vertices.sort((v1, v2) -> g.inDegree(v2) - g.inDegree(v1));
        return vertices;
	}
}


