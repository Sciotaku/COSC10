import java.io.*;
import java.util.*;

/**
 * Class that includes methods to read and extract information from files in desired format.
 * @author Rahul Gupta, Dartmouth Class of 2026
 * @author Alexander Marcoux, Dartmouth Class of 2026
 * Got help from chatGPT.
 */

public class ParseFiles {
    public static Map<Integer, String> IDToName(String pathName) throws IOException {
        Map<Integer, String> result = new HashMap<>();
        try (BufferedReader input = new BufferedReader(new FileReader(pathName))) {
            String line;
            while ((line = input.readLine()) != null) {
                String[] s = line.split("\\|");
                result.put(Integer.parseInt(s[0]), s[1]);
            }
        }
        return result;
    }

    public static Graph<String, Set<String>> buildGraph(String pathName, String actorsFile, String moviesFile) throws Exception  {
        Graph<String, Set<String>> ActorMovieGraph = new AdjacencyMapGraph<>();
        Map<String, Set<String>> actorMovieMap = new HashMap<>();

        // Parse actors and movies
        Map<Integer, String> actors = IDToName(actorsFile);
        Map<Integer, String> movies = IDToName(moviesFile);

        // Create a map of actors and movies they appeared in
        try {
            BufferedReader input = new BufferedReader(new FileReader(pathName));
            String line;
            while ((line = input.readLine()) != null) {
                String[] s = line.split("\\|");
                String movieID = s[0];
                String actorID = s[1];
                String actorName = actors.get(Integer.parseInt(actorID));
                String movieName = movies.get(Integer.parseInt(movieID));

                // Update actor-movie map
                Set<String> moviesWithActor = actorMovieMap.get(actorName);
                if (moviesWithActor == null) {
                    moviesWithActor = new HashSet<>();
                }
                moviesWithActor.add(movieName);
                actorMovieMap.put(actorName, moviesWithActor);
            }
            input.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Add vertices to graph
        for (String actor : actorMovieMap.keySet()) {
            ActorMovieGraph.insertVertex(actor);
        }

        // Add edges to graph
        for (String actor1 : actorMovieMap.keySet()) {
            Set<String> movies1 = actorMovieMap.get(actor1);
            for (String actor2 : actorMovieMap.keySet()) {
                if (actor1.equals(actor2)) continue;

                Set<String> movies2 = actorMovieMap.get(actor2);
                Set<String> commonMovies = new HashSet<>(movies1);
                commonMovies.retainAll(movies2);

                if (!commonMovies.isEmpty()) {
                    ActorMovieGraph.insertUndirected(actor1, actor2, commonMovies);
                }
            }
        }

        return ActorMovieGraph;
    }

    public static void main(String[] args) {
        try {
            // Parse actors and movies
            Map<Integer, String> actors = IDToName("bacon/actorsTest.txt");
            Map<Integer, String> movies = IDToName("bacon/moviesTest.txt");

            System.out.println(actors);
            System.out.println(movies);

            Graph<String, Set<String>> MovieActorGraph = buildGraph("bacon/movie-actorsTest.txt", "bacon/actorsTest.txt", "bacon/moviesTest.txt");
            System.out.println(MovieActorGraph);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

