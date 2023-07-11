import java.io.*;
import java.util.*;

public class HuffmanEncoding implements Huffman {
    Map<Character, Long> charFreq = new HashMap<>();
    Comparator<BinaryTree<CodeTreeElement>> comparator = new TreeComparator();
    PriorityQueue<BinaryTree<CodeTreeElement>> priorityQueue = new PriorityQueue<>(comparator);

    public HuffmanEncoding() {
    }

    /**
     * Read file provided in pathName and count how many times each character appears
     *
     * @param pathName - path to a file to read
     * @return - Map with a character as a key and the number of times the character appears in the file as value
     * @throws IOException
     */
    public Map<Character, Long> countFrequencies(String pathName) throws IOException {

        try (BufferedReader inputFile = new BufferedReader(new FileReader(pathName))) {
            int i;
            while ((i = inputFile.read()) != -1) {
                char c = (char) i;
                // if the frequency map does not contain our character, add character to map with a value of 1
                if (!charFreq.containsKey(c)) {
                    charFreq.put(c, 1L);
                }
                // if the character already exists in the frequency table, update its frequency and add iterate
                else {
                    Long freq = charFreq.get(c);
                    freq++;
                    charFreq.put(c, freq);
                }
            }
            return charFreq;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return charFreq;
    }

    /**
     * Construct a code tree from a map of frequency counts. Note: this code should handle the special
     * cases of empty files or files with a single character.
     *
     * @param frequencies a map of Characters with their frequency counts from countFrequencies
     * @return the code tree.
     */
    public BinaryTree<CodeTreeElement> makeCodeTree(Map<Character, Long> frequencies) {
        BinaryTree<CodeTreeElement> root, left, right;
        Set<Character> charList = charFreq.keySet();

        for (char c: charList) {
            // Create a new CodeTreeElement object and add it to its own tree
            CodeTreeElement newTree = new CodeTreeElement(charFreq.get(c), c);
            BinaryTree<CodeTreeElement> charTree = new BinaryTree<>(newTree);
            // Add tree to priority queue
            priorityQueue.add(charTree);
        }

        if (charFreq.size() == 0) {
            return null;
        }
        // Merging nodes until only one root node remains
        while (priorityQueue.size() > 1) {
            left = priorityQueue.remove();
            right = priorityQueue.remove();
            root = new BinaryTree<>(new CodeTreeElement(left.getData().getFrequency() + right.getData().getFrequency(), '\0'), left, right);
            priorityQueue.add(root);

        }
        return priorityQueue.remove();
    }

    /**
     * Computes the code for all characters in the tree and enters them
     * into a map where the key is a character and the value is the code of 1's and 0's representing
     * that character.
     *
     * @param codeTree the tree for encoding characters produced by makeCodeTree
     * @return the map from characters to codes
     */
    public Map<Character, String> computeCodes(BinaryTree<CodeTreeElement> codeTree) {
        Map<Character, String> codes = new HashMap<>();
        codeTreeHelper(codeTree, "", codes);
        return codes;
    }

    public void codeTreeHelper(BinaryTree<CodeTreeElement> codeTree, String s, Map<Character, String> codes) {
        if (codeTree.size() == 0) {
            return;
        }
        if (codeTree.isLeaf()) {
            codes.put(codeTree.getData().getChar(), s);
        }
        else {
            if (codeTree.hasLeft()) {
                codeTreeHelper(codeTree.getLeft(), s + "0", codes);  // left edge = 0
            }
            if (codeTree.hasRight()) {
                codeTreeHelper(codeTree.getRight(), s + "1", codes);  // right edge = 1
            }
        }
    }

    public void compressFile(Map<Character, String> codeMap, String pathName, String compressedPathName) throws IOException {
        try {
            BufferedReader input = new BufferedReader(new FileReader(pathName));
            BufferedBitWriter bitOutput = new BufferedBitWriter(compressedPathName);

            int j = input.read();

            // Empty file
            if (j == -1) {
                return;
            }

            char ch = (char) j;
            String bit = codeMap.get(ch);
            bitOutput.writeBit(true); // There is at least one character in the file

            for (char b : bit.toCharArray()) {
                if (b == '1') {
                    bitOutput.writeBit(true);
                }
                else if (b == '0') {
                    bitOutput.writeBit(false);
                }
            }

            while ((j = input.read()) != -1) {
                ch = (char) j;
                bit = codeMap.get(ch);

                for (char b : bit.toCharArray()) {
                    if (b == '1') {
                        bitOutput.writeBit(true);
                    }
                    else if (b == '0') {
                        bitOutput.writeBit(false);
                    }
                }
            }

            input.close();
            bitOutput.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error in Loading Files");
        }
    }

    /**
     * Decompress file compressedPathName and store plain text in decompressedPathName.
     * @param compressedPathName - file created by compressFile
     * @param decompressedPathName - store the decompressed text in this file, contents should match the original file before compressFile
     * @param codeTree - Tree mapping compressed data to characters
     * @throws IOException
     */
    public void decompressFile(String compressedPathName, String decompressedPathName, BinaryTree<CodeTreeElement> codeTree) throws IOException {
        try {
            BufferedBitReader bitInput = new BufferedBitReader(compressedPathName);
            BufferedWriter output = new BufferedWriter(new FileWriter(decompressedPathName));
            BinaryTree<CodeTreeElement> current = codeTree;

            // Check if the file is empty
            if (!bitInput.hasNext()) {
                output.close();
                bitInput.close();
                return;
            }

            while (bitInput.hasNext()) {
                boolean bit = bitInput.readBit();
                if (bit) {
                    current = current.getRight();
                }
                else {
                    current = current.getLeft();
                }
                if (current.isLeaf()) {
                    char c = current.getData().getChar();
                    output.write(c);
                    current = codeTree;
                }
            }
            bitInput.close();
            output.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error in Loading Files");
        }
    }

    public static void main(String[] args) throws IOException {
//        // Test 1 Reading an empty file
//        HuffmanEncoding empty = new HuffmanEncoding();
//        Map<Character, Long> test1 = empty.countFrequencies("compression/emptyfile.txt");
//        BinaryTree<CodeTreeElement> result1 = empty.makeCodeTree(test1);
//        System.out.println(result1);
//        Map<Character, String> testCode1 = empty.computeCodes(result1);
//        System.out.println(testCode1);
//        empty.compressFile(testCode1, "compression/emptyfile.txt", "compression/emptyfile_compressed.txt");
//        empty.decompressFile("compression/emptyfile_compressed.txt", "compression/emptyfile_decompressed.txt", result1);
//
//        // Test 2 Reading file with a single character
//        HuffmanEncoding single = new HuffmanEncoding();
//        Map<Character, Long> test2 = single.countFrequencies("compression/singlecharacter.txt");
//        BinaryTree<CodeTreeElement> result2 = single.makeCodeTree(test2);
//        System.out.println(result2);
//        Map<Character, String> testCode2 = single.computeCodes(result2);
//        System.out.println(testCode2);
//        single.compressFile(testCode2, "compression/emptyfile.txt", "compression/singlecharacter_compressed.txt");
//        single.decompressFile("compression/singlecharacter_compressed.txt", "compression/singlecharacter_decompressed.txt", result2);
//        // The second test case is not working.

//        // Test Case 3 Reading a one sentence file
//        HuffmanEncoding test = new HuffmanEncoding();
//        String original = "compression/onesentence.txt";
//        String compressed = "compression/onesentence_compressed.txt";
//        String decompressed = "compression/onesentence_decompressed.txt";
//        Map<Character, Long> m = test.countFrequencies(original);
//        System.out.println(m);
//        BinaryTree<CodeTreeElement> result = test.makeCodeTree(m);
//        System.out.println(result);
//        Map<Character, String> coder = test.computeCodes(result);
//        System.out.println(coder);
//        test.compressFile(coder, original, compressed);
//        test.decompressFile(compressed, decompressed, result);

//        /// Test 4 USConstitution.txt
//        HuffmanEncoding USConstitution = new HuffmanEncoding();
//        String USCon = "compression/USConstitution.txt";
//        String compressedUSCon = "compression/USConstitution_compressed.txt";
//        String decompressedUSCon = "compression/USConstitution_decompressed.txt";
//        Map<Character, Long> freq1 = USConstitution.countFrequencies(USCon);
//        BinaryTree<CodeTreeElement> result1 = USConstitution.makeCodeTree(freq1);
//        Map<Character, String> codes1 = USConstitution.computeCodes(result1);
//        USConstitution.compressFile(codes1, USCon, compressedUSCon);
//        USConstitution.decompressFile(compressedUSCon, decompressedUSCon, result1);
//
//        // Test 4 WarAndPeace.txt
//        HuffmanEncoding WarAndPeace = new HuffmanEncoding();
//        String WAP = "compression/WarAndPeace.txt";
//        String compressedWAP = "compression/WarAndPeace_compressed.txt";
//        String decompressedWAP = "compression/WarAndPeace_decompressed.txt";
//        Map<Character, Long> freq2 = WarAndPeace.countFrequencies(WAP);
//        BinaryTree<CodeTreeElement> result2 = WarAndPeace.makeCodeTree(freq2);
//        Map<Character, String> codes2 = WarAndPeace.computeCodes(result2);
//        WarAndPeace.compressFile(codes2, WAP, compressedWAP);
//        WarAndPeace.decompressFile(compressedWAP, decompressedWAP, result2);
    }
}






