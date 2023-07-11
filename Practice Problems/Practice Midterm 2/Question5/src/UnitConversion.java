import java.util.*;

public class UnitConversion {


    // 5.1
    public Map<String, String> bfs(AdjacencyMapGraph<String, Double> unitMap, String startUnit) {
        Map<String, String> backtrack = new HashMap<>();
        Set<String> visited = new HashSet<>();

        Queue<String> queue = new LinkedList<>();
        queue.add(startUnit);
        visited.add(startUnit);

        // bfs
        while(!queue.isEmpty()) {
            String curr = queue.poll();

            for(String neighbor : unitMap.outNeighbors(curr)) {
                if(visited.contains(neighbor)) continue;

                visited.add(neighbor);
                queue.add(neighbor);
                backtrack.put(neighbor, curr);
            }
        }

        return backtrack;
    }

    // 5.2
    public List<String> getPath(Map<String, String> backtrackMap, String endUnit) {
        List<String> path = new ArrayList<>();

        // traverse the path from the end back to the start
        String curr = endUnit;
        while(backtrackMap.containsKey(curr)) {
            path.add(0, curr);
            curr = backtrackMap.get(curr);
        }

        path.add(0, curr);

        return path;
    }

    // 5.3
    public double conversion(AdjacencyMapGraph<String, Double> unitMap, List<String> path, double startValue) {
        double value = startValue;

        // take each pair of units and get the labels
        for(int i = 0; i < path.size() - 1; i++) {
            value *= (unitMap.getLabel(path.get(i), path.get(i+1)));
        }

        return value;
    }

    // tests
    public static void main(String[] args) {
        AdjacencyMapGraph<String, Double> map = new AdjacencyMapGraph<>();
        map.insertVertex("inch");
        map.insertVertex("foot");
        map.insertVertex("mile");
        map.insertVertex("kilometer");

        map.insertDirected("inch", "foot", 12.0);
        map.insertDirected("foot", "inch", 1.0/12);
        map.insertDirected("foot", "mile", 5280.0);
        map.insertDirected("mile", "foot", 1.0/5280);
        map.insertDirected("mile", "kilometer", 0.621371);
        map.insertDirected("kilometer", "mile", 1.60934);

        UnitConversion uc = new UnitConversion();

        Map<String, String> backtrack = uc.bfs(map, "inch");
        List<String> path = uc.getPath(backtrack, "kilometer");
        System.out.println(uc.conversion(map, path, 1));

    }

}
