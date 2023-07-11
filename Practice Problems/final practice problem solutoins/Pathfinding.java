import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Solutions to problem 3 of cs10 final practice exam
 * @author warrenshepard, Spring 2023
 */


public class Pathfinding {
    private AdjacencyMapGraph<String, Integer> graph;
    private HashMap<String, Integer> distances;
    HashMap<String, String> predecesors;

    public void loadgrpah(String pathname) {
        graph = new AdjacencyMapGraph<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathname));
            String line;
            String array[];

            while ((line = reader.readLine()) != null) {
                array = line.split(" \\| ");
                String city1 = array[0];
                String city2 = array[1];
                int distance = Integer.parseInt(array[2]);

                if (!graph.hasVertex(city1)) {
                    graph.insertVertex(city1);
                }
                if (!graph.hasVertex(city2)) {
                    graph.insertVertex(city2);
                }
                graph.insertUndirected(city1, city2, distance);
            }
            System.out.println(graph);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Graph<String, Integer> bfs(String source) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<String>();
        Graph<String, Integer> pathtree = new AdjacencyMapGraph<>();

        queue.add(source);
        visited.add(source);
        pathtree.insertVertex(source);

        while (!queue.isEmpty()) {
            String u = queue.remove();
            for (String v : graph.outNeighbors(u)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                    pathtree.insertVertex(v);
                    pathtree.insertDirected(v, u, null);
                }
            }
        }
        return pathtree;
    }

    public List<String> getBFSPath(Graph<String, Integer> pathtree, String city) {
        ArrayList<String> path = new ArrayList<>();
        if (pathtree.numVertices() == 0 || !pathtree.hasVertex(city)) {     // check to make sure a path can be found
            return null;
        }
        String current = city;
        path.add(current);
        while (pathtree.outDegree(current) > 0) {

            for (String u : pathtree.outNeighbors(current)) {
                path.add(u);
                current = u;
            }
        }
        return path;
    }

    public void dijkstras(String source) {
        PriorityQueue<String> queue = new PriorityQueue<>((c1, c2) -> distances.get(c2) - distances.get(c1));
        predecesors = new HashMap<>();
        distances = new HashMap<>();

        for (String city : graph.vertices()) {
            //System.out.println(city);
            predecesors.put(city, null);
            distances.put(city, 100000);
            queue.add(city);
        }
        distances.put(source, 0);

        while (!queue.isEmpty()) {
            String u = queue.poll();
            for (String v: graph.outNeighbors(u)) {
                if (distances.get(u) + graph.getLabel(u, v) < distances.get(v)) {
                    distances.put(v, (distances.get(u) + graph.getLabel(u, v)));
                    predecesors.put(v, u);
                }
            }
        }
    }

    public ArrayList<String> getDijkstrasPath(String source, String city) {
        ArrayList<String> path = new ArrayList<>();
        dijkstras(source);

        String current = city;
        path.add(current);
        while (!predecesors.get(current).equals(source)) {
            path.add(0, current);
            current = predecesors.get(current);
        }
        path.add(0, source);
        return path;
    }

    public static void main(String[] args) {
        Pathfinding pathfinder = new Pathfinding();
        pathfinder.loadgrpah("/Users/warrenshepard/IdeaProjects/cs10_23S/final practice problem solutoins/cities.txt");

        Graph<String, Integer> pathtree = pathfinder.bfs("Hanover");
        System.out.println("BFS: " + pathfinder.getBFSPath(pathtree, "Middleton"));
        System.out.println("Dijkstras: " + pathfinder.getDijkstrasPath("Hanover", "Middleton"));


    }
}

/**
 *  ANSWER TO 3.6: Dijkstras is a better algorithim becuase it takes edge weights into consideration. A path can be
 *  shorter in distance even if it has more nodes than another path. Since bfs does not consider edge weights, its
 *  limitation is that it does not always return the shortest physical path.
 *
 *  The only case where using BFS is better is if edge weights are not supplied/necessary or if you'd rather traverse
 *  the shortest number of nodes rather than the shortest distance (a practical example of this might be stopping
 *  at the fewest airports on a long trip even if it means an hour or so of extra travel time).
 */
