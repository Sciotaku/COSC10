// q1

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SkiLift {

    // 1.1
    private List<Queue<List<String>>> groupLines;
    private Queue<String> singlesLine;
    private int numLines; // optional

    public SkiLift(int n) {
        this.numLines = n;
        this.groupLines = new ArrayList<>();
        this.singlesLine = new LinkedList<>(); // LinkedList can be an implementation of queue in Java

        // add queues into the group lines
        for(int i = 0; i < n; i++) {
            groupLines.add(new LinkedList<>());
        }
    }


    // 1.2
    public void addSkiers(List<String> skiers) throws Exception { // assume 1 <= skiers.size() <= 4

        if(skiers.size() < 1) throw new Exception ("There must be at least 1 skier in a group!");
        if(skiers.size() > 4) throw new Exception ("There can be at most 4 skiers in a group!");

        if(skiers.size() == 1) {
            singlesLine.add(skiers.get(0)); // add equivalent to "enqueue"
            return;
        }

        // otherwise get the shortest line and add the group to it
        Queue<List<String>> shortestLine = shortestLine();
        shortestLine.add(skiers);
    }

    // helper method for 1.2
    // very similar to 'getLargestRegion' from pset 1
    private Queue<List<String>> shortestLine() {
        Queue<List<String>> shortest = groupLines.get(0);

        for(int i = 1; i < groupLines.size(); i++) {
            if(groupLines.get(i).size() < shortest.size()) {
                shortest = groupLines.get(i);
            }
        }

        return shortest;
    }


    // 1.3
    public List<String> loadLift() {

        // checking that at least one lift line has people
        if(emptyGroupLines()) {
            if(singlesLine.isEmpty()) {
                return new ArrayList<>();
            } else {

                List<String> lift = new ArrayList<>();

                // get a lift line of singles
                for(int i = 0; i < 4 && !singlesLine.isEmpty(); i++) {
                    lift.add(singlesLine.poll()); // poll equivalent to "dequeue"
                }

                return lift;

            }
        }

        // otherwise get a group from a line
        Queue<List<String>> randomLine = getRandomLine();
        List<String> group = randomLine.poll();

        while(group.size() < 4 && !singlesLine.isEmpty()) {
            group.add(singlesLine.poll());
        }

        return group;
    }

    // helper method for 1.3
    private Queue<List<String>> getRandomLine() {
        Queue<List<String>> currLine = new LinkedList<>();

        // by repeating this, we can ensure that the line will have a group in it
        while(currLine.size() == 0) {
            int nextLineIndex = (int) (Math.random() * numLines);
            currLine = groupLines.get(nextLineIndex);
        }

        return currLine;
    }

    // helper method for 1.3
    private boolean emptyGroupLines() {

        // if any line has a group in it then not all group lines are empty
        for(Queue<List<String>> line : groupLines) {
            if(line.size() > 0) return false;
        }

        return true;
    }


    // testing
    public static void main(String[] args) {
        SkiLift sl = new SkiLift(3);

        List<String> g1 = new ArrayList<>();
        g1.add("Thomas");
        g1.add("Tanya");
        g1.add("Tristan");

        List<String> g2 = new ArrayList<>();
        g2.add("Christian");
        g2.add("Caroline");

        List<String> g3 = new ArrayList<>();
        g3.add("Xander");

        List<String> g4 = new ArrayList<>();
        g4.add("Alex");
        g4.add("Allison");
        g4.add("Asher");
        g4.add("Abigail");

        List<String> g5 = new ArrayList<>();
        g5.add("Sean");
        g5.add("Sally");
        g5.add("Sebastian");

        List<String> g6 = new ArrayList<>();
        g6.add("Paul");

        List<String> tooManySkiers = new ArrayList<>();
        tooManySkiers.add("A");
        tooManySkiers.add("B");
        tooManySkiers.add("C");
        tooManySkiers.add("D");
        tooManySkiers.add("E");

        try {
            sl.addSkiers(g1);
            sl.addSkiers(g2);
            sl.addSkiers(g3);
            sl.addSkiers(g4);
            sl.addSkiers(g5);
            sl.addSkiers(g6);
        } catch(Exception e) {
            System.out.println(e);
        }

        try {
            sl.addSkiers(tooManySkiers);
        } catch(Exception e) {
            System.out.println(e);
        }

        for(int i = 0; i < 5; i++) {
            System.out.println(sl.loadLift());
        }
    }

}
