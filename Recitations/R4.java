// Let's build up a map to keep track of students and their courses. We'll just use Strings for the names of students and courses.
//        a. Write the Java type of such a map
//        b. Create a new map
//        c. Add yourself and an empty course list to the map
//        d. Add CS10 to your course list in the map

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class R4 {
    public static void main(String[] args){
        Map<String, ArrayList<String>> myMap = new TreeMap<String, ArrayList<String>>();
        myMap.put("Rahul Gupta", new ArrayList<String>());
        ArrayList<String> courses = myMap.get("Rahul Gupta");
        courses.add("PHYS 40");
        courses.add("PHYS 43");
        courses.add("ENGS 22");
        System.out.println(myMap);
    }
}
