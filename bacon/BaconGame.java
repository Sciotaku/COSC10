import java.util.*;

/**
 * Bacon game implementation, with a command-line interface.
 * @author Rahul Gupta, Dartmouth Class of 2026
 * @author Alexander Marcoux, Dartmouth Class of 2026
 * Got help from Aravind Kandasamy '26 and Joshua Piesner '26.
 * The separation method is not working.
 */

public class BaconGame {
    private static Graph<String, Set<String>> ActorMoviesGraph;
    private static Graph<String, Set<String>> newGraph;
    private static String centerOfUniverse;

    public static void center(int n) {
        if (n == 0) {
            System.out.println("Cannot sort on a list of zero elements!");
            return;
        }

        ArrayList<String> vertices = new ArrayList<>();
        Map<String, Integer> nameToNum = new HashMap<>();

        for (String current : ActorMoviesGraph.vertices()) {
            vertices.add(current);
            int val = (int) GraphLib.averageSeparation(GraphLib.bfs(ActorMoviesGraph, current), current);
            nameToNum.put(current, val);
        }

        System.out.println("Sorted actors by average separation:");
        vertices.sort(Comparator.comparingInt(nameToNum::get));

        if (n > 0) {
            System.out.println("Best connected " + n + " actors: ");
            for (int i = 0; i < n; i++) {
                System.out.println(i + ". " + vertices.get(i));
            }
        }
        else {
            System.out.println("Worst connected " + Math.abs(n) + " actors: ");
            for (int i = vertices.size() - 1; i > vertices.size() - 1 + n; i--) {
                System.out.println(i + ". " + vertices.get(i));
            }
        }
    }

    public static void sortByDegree(int lowNum, int highNum) {
        ArrayList<String> vertices = new ArrayList<>();
        for(String current: ActorMoviesGraph.vertices()) {
            vertices.add(current);
        }
        vertices.sort((actor1, actor2) -> -(ActorMoviesGraph.inDegree(actor1) - ActorMoviesGraph.inDegree(actor2)));

        int i = 0;
        for (String s : vertices) {
            int degree = ActorMoviesGraph.inDegree(s);
            if (degree >= lowNum && degree <= highNum) {
                System.out.println(i + ". " + s);
                i++;
            }
        }
    }

    public static void infiniteSeparation() {
        if (centerOfUniverse == null) {
            System.out.println("Please begin by setting a center first");
            return;
        }

        System.out.println("Vertices with infinite separation");
        Graph<String, Set<String>> subGraph = GraphLib.bfs(ActorMoviesGraph, centerOfUniverse);
        Set<String> requiredVertices = GraphLib.missingVertices(ActorMoviesGraph, subGraph);

        int i = 0;
        for (String s : requiredVertices) {
            System.out.println(i + ". " + s);
            i++;
        }
    }

    public static void path(String target) {
        if (centerOfUniverse == null) {
            System.out.println("Please begin by setting a center first");
            return;
        }
        // Find shortest path from center to target
        Graph<String, Set<String>> shortestPath = GraphLib.bfs(ActorMoviesGraph, centerOfUniverse);
        List<String> path = GraphLib.getPath(shortestPath, target);

        if (path.isEmpty()) {
            System.out.printf("No path exists from %s to %s\n", centerOfUniverse, target);
            return;
        }

        // Print path and game message
        System.out.println(centerOfUniverse + " game >");
        System.out.println(target + " number is " + (path.size() - 1));
        for (int i = 0; i < path.size() - 1; i++) {
            String actor1 = path.get(i);
            String actor2 = path.get(i + 1);
            Set<String> movies = ActorMoviesGraph.getLabel(actor1, actor2);
            String movie = movies.iterator().next();
            System.out.printf(actor1 + " appeared in " + movie + " with " + actor2 + "\n");
        }
    }

    public static void separation(int lowNum, int highNum){
        if(centerOfUniverse == null) {
            System.out.println("Add the center of the universe first using the command u");
            return;
        }

        List<String> vertices = new ArrayList<>();
        for (String actor : ActorMoviesGraph.vertices()) {
            if (actor.equals(centerOfUniverse)) {
                continue; // Skip center of universe
            }
            List<String> path = GraphLib.getPath(newGraph, actor);
            int pathLength = path.size() - 1; // Exclude center of universe
            if (pathLength >= lowNum && pathLength <= highNum) {
                vertices.add(actor);
            }
        }

        if (vertices.isEmpty()) {
            System.out.println("No vertices of specified path length.");
            return;
        }

        vertices.sort((actor1, actor2) -> {
            int pathLength1 = GraphLib.getPath(newGraph, actor1).size() - 1; // Exclude center of universe
            int pathLength2 = GraphLib.getPath(newGraph, actor2).size() - 1; // Exclude center of universe
            return pathLength2 - pathLength1; // Sort in descending order of path length
        });

        int i = 1;
        for (String actor : vertices) {
            int pathLength = GraphLib.getPath(newGraph, actor).size() - 1; // Exclude center of universe
            System.out.println(i + ". " + actor + " (" + pathLength + ")");
            i++;
        }
    }

    public static void universeCenter(String s) {
        if (!ActorMoviesGraph.hasVertex(s)) {
            System.out.println("This actor isn't in the list.");
            return;
        }
        newGraph = GraphLib.bfs(ActorMoviesGraph, s);
        centerOfUniverse = s;
        System.out.println(s + " is now the center of the universe.");
    }

    public static void quit() {
        Runtime.getRuntime().exit(1);
    }

    public static void main(String[] args) throws Exception {
        ActorMoviesGraph = ParseFiles.buildGraph("bacon/movie-actors.txt", "bacon/actors.txt", "bacon/movies.txt");

        System.out.println("Commands:\n" +
                "c <#>: list top (positive number) or bottom (negative) <#> centers of the universe, sorted by average separation\n" +
                "d <low> <high>: list actors sorted by degree, with degree between low and high\n" +
                "i: list actors with infinite separation from the current center\n" +
                "p <name>: find path from <name> to current center of the universe\n" +
                "s <low> <high>: list actors sorted by non-infinite separation from the current center, with separation between low and high\n" +
                "u <name>: make <name> the center of the universe\n" +
                "q: quit game");

        Scanner userInput = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String command = userInput.nextLine();

            // c <#>: list top (positive number) or bottom (negative) <#> centers of the universe, sorted by average separation
            switch (command) {
                case "c":
                    System.out.println("Enter a number:");
                    Scanner Num = new Scanner(System.in);
                    int num = Num.nextInt();
                    center(num);
                    break;
                // d <low> <high>: list actors sorted by degree, with degree between low and high
                case "d": {
                    System.out.println("Enter low:");
                    Scanner setLow = new Scanner(System.in);
                    int low = setLow.nextInt();
                    System.out.println("Enter high:");
                    Scanner setHigh = new Scanner(System.in);
                    int high = setHigh.nextInt();
                    sortByDegree(low, high);
                    break;
                }
                // i: list actors with infinite separation from the current center
                case "i":
                    infiniteSeparation();
                    break;
                // p <name>: find path from <name> to current center of the universe
                case "p":
                    System.out.println("Enter vertex: ");
                    Scanner setTarget = new Scanner(System.in);
                    String target = setTarget.nextLine();
                    path(target);
                    break;
                // s <low> <high>: list actors sorted by non-infinite separation from the current center, with separation between low and high
                case "s": {
                    System.out.println("Enter low separation:");
                    Scanner setLow = new Scanner(System.in);
                    int low = setLow.nextInt();
                    System.out.println("Enter high separation:");
                    Scanner setHigh = new Scanner(System.in);
                    int high = setHigh.nextInt();
                    separation(low, high);
                    break;
                }
                // u <name>: make <name> the center of the universe
                case "u":
                    System.out.println("Enter center of the universe:");
                    Scanner setCenter = new Scanner(System.in);
                    String center = setCenter.nextLine();
                    universeCenter(center);
                    break;
                // q: quit game
                case "q":
                    quit();
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
        }
    }
}


