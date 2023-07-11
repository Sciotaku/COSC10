import java.security.InvalidKeyException;
import java.util.*;

/**
 * Generic binary search tree
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Fall 2016, min
 *
 * @editor Ethan Chen, edited to remove all generics and replace with
 *      key type Integer and value type String
 */

public class BST {


    /** AUTHOR: ETHAN CHEN */
    /** MAIN METHOD */

    // 3.2
    public String closestValInBST(Integer targetKey) {

        Integer closestKey = key;
        String bestValue = value;

        // set initial node to top tree
        BST traversal = this;
        while(traversal != null) {

            // if we hit the target key, return its value
            if(targetKey == traversal.key) return traversal.value;

            int diff = targetKey - traversal.key;

            // if the current node is better, set it as so
            if(Math.abs(diff) < closestKey) {
                closestKey = Math.abs(diff);
                bestValue = traversal.value;
            }

            // if target key less, need to move left (decrease), otherwise right
            if(diff < 0) {
                traversal = traversal.left;
            } else {
                traversal = traversal.right;
            }
        }

        return bestValue;

    }

    /** END MODIFICATIONS */



    private Integer key;
    private String value;
    private BST left, right;

    /**
     * Constructs leaf node -- left and right are null
     */
    public BST(Integer key, String value) {
        this.key = key; this.value = value;
    }

    /**
     * Constructs inner node
     */
    public BST(Integer key, String value, BST left, BST right) {
        this.key = key; this.value = value;
        this.left = left; this.right = right;
    }

    /**
     * Is it a leaf node?
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Does it have a left child?
     */
    public boolean hasLeft() {
        return left != null;
    }

    /**
     * Does it have a right child?
     */
    public boolean hasRight() {
        return right != null;
    }

    /**
     * Returns the value associated with the search key, or throws an exception if not in BST
     */
    public String find(Integer search) throws InvalidKeyException {
        System.out.println(key); // to illustrate
        int compare = search.compareTo(key);
        if (compare == 0) return value;
        if (compare < 0 && hasLeft()) return left.find(search);
        if (compare > 0 && hasRight()) return right.find(search);
        throw new InvalidKeyException(search.toString());
    }

    /**
     * Smallest key in the tree, recursive version
     */
    public Integer min() {
        if (left != null) return left.min();
        return key;
    }

    /**
     * Smallest key in the tree, iterative version
     */
    public Integer minIter() {
        BST curr = this;
        while (curr.left != null) curr = curr.left;
        return curr.key;
    }

    /**
     * Inserts the key & value into the tree (replacing old key if equal)
     */
    public void insert(Integer key, String value) {
        int compare = key.compareTo(this.key);
        if (compare == 0) {
            // replace
            this.value = value;
        }
        else if (compare < 0) {
            // insert on left (new leaf if no left)
            if (hasLeft()) left.insert(key, value);
            else left = new BST(key, value);
        }
        else if (compare > 0) {
            // insert on right (new leaf if no right)
            if (hasRight()) right.insert(key, value);
            else right = new BST(key, value);
        }
    }

    /**
     * Deletes the key and returns the modified tree, which might not be the same object as the original tree
     * Thus must afterwards just use the returned one
     */
    public BST delete(Integer search) throws InvalidKeyException {
        int compare = search.compareTo(key);
        if (compare == 0) {
            // Easy cases: 0 or 1 child -- return other
            if (!hasLeft()) return right;
            if (!hasRight()) return left;
            // If both children are there, delete and substitute the successor (smallest on right)
            // Find it
            BST successor = right;
            while (successor.hasLeft()) successor = successor.left;
            // Delete it
            right = right.delete(successor.key);
            // And take its key & value
            this.key = successor.key;
            this.value = successor.value;
            return this;
        }
        else if (compare < 0 && hasLeft()) {
            left = left.delete(search);
            return this;
        }
        else if (compare > 0 && hasRight()) {
            right = right.delete(search);
            return this;
        }
        throw new InvalidKeyException(search.toString());
    }

    /**
     * Parenthesized representation:
     * <tree> = "(" <tree> ["," <tree>] ")" <key> ":" <value>
     *        | <key> ":" <value>
     */
    @Override
    public String toString() {
        if (isLeaf()) return key+":"+value;
        String s = "(";
        if (hasLeft()) s += left;
        else s += "_";
        s += ",";
        if (hasRight()) s += right;
        else s += "_";
        return s + ")" + key+":"+value;
    }


    // tester
    public static void main(String[] args) {
        // valid BST
        BST t1 = new BST(1, "A");
        BST t2 = new BST(1000, "B");
        BST t3 = new BST(100, "C", t1, t2);
        BST t4 = new BST(50000, "D");
        BST t5 = new BST(1000000, "E");
        BST t6 = new BST(100000, "F", t4, t5);
        BST t7 = new BST(10000, "G", t3, t6);

        System.out.println(t7.closestValInBST(999999));
        System.out.println(t7.closestValInBST(60));
        System.out.println(t7.closestValInBST(23));
        System.out.println(t7.closestValInBST(870));
        System.out.println(t7.closestValInBST(1));

    }
}