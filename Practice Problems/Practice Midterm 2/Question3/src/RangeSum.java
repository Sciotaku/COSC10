

import java.util.LinkedList;
import java.util.Queue;

public class RangeSum {

    // 3.1
    public int rangeSumRecursive(BinaryTree<Integer> tree, int low, int high) {
        int sum = 0;

        // if in range add its sum
        if(tree.getData() >= low && tree.getData() <= high) {
            sum += tree.getData();
        }

        // add the rangeSum from children
        if(tree.hasLeft()) sum += rangeSumRecursive(tree.getLeft(), low, high);
        if(tree.hasRight()) sum += rangeSumRecursive(tree.getRight(), low, high);

        return sum;
    }

    public int rangeSumIterative(BinaryTree<Integer> tree, int low, int high) {

        // queue to bfs the tree
        Queue<BinaryTree> queue = new LinkedList<>();
        queue.add(tree);

        int sum = 0;

        while(!queue.isEmpty()) {
            BinaryTree<Integer> current = queue.poll();

            // add to the sum if in range
            if(current.getData() >= low && current.getData() <= high) {
                sum += current.getData();
            }

            // add children to queue if they exist
            if(current.hasLeft()) queue.add(current.getLeft());
            if(current.hasRight()) queue.add(current.getRight());
        }

        return sum;
    }

    // tester
    public static void main(String[] args) {
        BinaryTree<Integer> t1 = new BinaryTree<>(3);
        BinaryTree<Integer> t2 = new BinaryTree<>(4);
        BinaryTree<Integer> t3 = new BinaryTree<>(7, t1, t2);
        BinaryTree<Integer> t4 = new BinaryTree<>(2);
        BinaryTree<Integer> t5 = new BinaryTree<>(8);
        BinaryTree<Integer> t6 = new BinaryTree<>(1, t4, t5);
        BinaryTree<Integer> t7 = new BinaryTree<>(10, t3, t6);

        RangeSum rs = new RangeSum();

        System.out.println(rs.rangeSumRecursive(t7, 2, 7));
        System.out.println(rs.rangeSumIterative(t7, 2, 7));




    }

}
