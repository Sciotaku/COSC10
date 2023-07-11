package Lec3;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        ArrayList<Ball> myList;
        myList = new ArrayList<Ball>();

        myList.add(new Ball(5, 6));
        System.out.println(myList.get(0));
    }
}
