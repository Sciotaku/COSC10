import java.util.ArrayList;
import java.util.List;

// 3.4

public class CountdownTree {

    int maxChildren;
    List<CountdownTree> children;
    int data;

    public CountdownTree(int data, List<CountdownTree> children, int maxChildren) {
        this.data = data;
        this.maxChildren = maxChildren;
        this.children = children;
    }

    public CountdownTree(int data, int maxChildren) {
        this.data = data;
        this.maxChildren = maxChildren;
        this.children = new ArrayList<>();
    }

    public void addChild(int data) throws Exception {
        if(children.size() == maxChildren) throw new Exception("All children already exist!");

        CountdownTree child = new CountdownTree(data, maxChildren - 1);
        children.add(child);
    }

    public CountdownTree getChild(int childIndex) throws Exception {
        if(childIndex < 0 || childIndex >= children.size()) throw new Exception("Invalid index!");

        return children.get(childIndex);
    }

    public int size() {
        int size = 1;

        for(CountdownTree child : children) {
            size += child.size();
        }

        return size;
    }

    public boolean isLeaf() {
        if(children.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void preorder() {
        System.out.println(data);

        for(CountdownTree child : children) {
            child.preorder();
        }
    }

    // tester
    public static void main(String[] args) {
        CountdownTree ct1 = new CountdownTree(1, 5);
        try {
            // valid adds
            ct1.addChild(2);
            ct1.addChild(3);
            ct1.addChild(4);
            ct1.addChild(5);
            ct1.addChild(6);
        } catch(Exception e) {

        }

        try {
            // invalid add
            ct1.addChild(100000);
        } catch(Exception e) {
            System.out.println(e);
        }

        try {
            CountdownTree ct2 = ct1.getChild(2);
            ct2.addChild(7);
            ct2.addChild(8);
            ct2.addChild(9);
            ct2.addChild(10);

            // this one should throw an exception
            ct2.addChild(100000);
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println(ct1.size());
        System.out.println(ct1.isLeaf());

        try {
            System.out.println(ct1.getChild(3).isLeaf());
        } catch(Exception e) {

        }

        System.out.println();
        ct1.preorder();


    }

}
