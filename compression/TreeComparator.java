import java.util.*;

public class TreeComparator implements Comparator<BinaryTree<CodeTreeElement>>{

    @Override
    public int compare(BinaryTree<CodeTreeElement> codeTree1, BinaryTree<CodeTreeElement> codeTree2) {
        if(codeTree1.getData().getFrequency() < codeTree2.getData().getFrequency()) {
            return -1;
        }
        else if(codeTree1.getData().getFrequency() > codeTree2.getData().getFrequency()) {
            return 1;
        }
        return 0;
    }
}

