import java.util.*;

public class HalveKTimes {

    // 4.1 - O(n*k)

    // 4.2 - O(nlogn + klogn)
    public List<Double> halveKTimes(List<Double> list, int k) {
        PriorityQueue<Double> values = new PriorityQueue<>((a, b) -> b.compareTo(a));

        // add all values to priority queue
        for(Double d : list) {
            values.add(d);
        }

        // k times, take off the smallest, halve it, and add it back to the queue
        for(int i = 0; i < k; i++) {
            Double smallest = values.poll();
            values.add(smallest/2);
        }

        // create a new list to store the new values and add them
        return new ArrayList<>(values);
    }

    // 4.3
    public List<Double> halveKTimesOrdered(List<Double> list, int k) {

        // map will correlate indices to double values
        Map<Integer, Double> map = new HashMap<>();

        // priority queue will store the indices, and sort by
        // correlated map value
        PriorityQueue<Integer> values = new PriorityQueue<>((a, b) -> map.get(b).compareTo(map.get(a)));

        for(int i = 0; i < list.size(); i++) {
            map.put(i, list.get(i));
            values.add(i);
        }

        // k times, take off smallest and halve it
        for(int i = 0; i < k; i++) {
            int smallestIndex = values.poll();
            map.put(smallestIndex, map.get(smallestIndex)/2);
            values.add(smallestIndex);
        }

        for(Integer index : map.keySet()) {
            list.set(index, map.get(index));
        }

        return list;
    }

    // tests
    public static void main(String[] args) {
        List<Double> list = new ArrayList<>();
        list.add(12.0);
        list.add(5.0);
        list.add(7.0);
        list.add(9.0);
        list.add(10.0);

        HalveKTimes hkt = new HalveKTimes();

        System.out.println(hkt.halveKTimes(list, 3));
        System.out.println(hkt.halveKTimesOrdered(list, 3));
    }

}
