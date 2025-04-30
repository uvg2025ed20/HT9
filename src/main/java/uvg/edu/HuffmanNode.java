package uvg.edu;

import java.io.*;
import java.util.*;

/**
 * Represents a node in the Huffman tree.
 * Each node contains a frequency, a character (for leaf nodes), and references to its left and right children.
 * Implements Serializable for saving and loading the tree structure.
 */
class HuffmanNode implements Serializable {
    int frequency; // Frequency of the character or sum of frequencies for internal nodes
    char character; // Character represented by the node (only for leaf nodes)
    HuffmanNode left; // Reference to the left child
    HuffmanNode right; // Reference to the right child
    boolean isLeaf; // Indicates if the node is a leaf

    /**
     * Constructs an internal HuffmanNode with the given frequency and child nodes.
     *
     * @param frequency The sum of frequencies of the child nodes.
     * @param left      The left child node.
     * @param right     The right child node.
     */
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
        this.isLeaf = false;
    }

    /**
     * Constructs a leaf HuffmanNode with the given frequency and character.
     *
     * @param frequency The frequency of the character.
     * @param character The character represented by the node.
     */
    public HuffmanNode(int frequency, char character) {
        this.frequency = frequency;
        this.character = character;
        this.left = null;
        this.right = null;
        this.isLeaf = true;
    }
}

/**
 * Comparator for HuffmanNode objects.
 * Compares nodes based on their frequency.
 */
class HuffmanComparator implements Comparator<HuffmanNode> {
    /**
     * Compares two HuffmanNode objects based on their frequency.
     *
     * @param node1 The first node to compare.
     * @param node2 The second node to compare.
     * @return A negative integer, zero, or a positive integer as the first node's frequency
     *         is less than, equal to, or greater than the second node's frequency.
     */
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return node1.frequency - node2.frequency;
    }
}

/**
 * Represents a Huffman tree used for encoding and decoding text.
 * Implements Serializable for saving and loading the tree structure.
 */
class HuffmanTree implements Serializable {
    private HuffmanNode root; // Root node of the Huffman tree
    private Map<Character, String> huffmanCodes; // Map of characters to their Huffman codes

    /**
     * Builds the Huffman tree based on character frequencies.
     *
     * @param frequencies A map of characters and their corresponding frequencies.
     */
    public void buildTree(Map<Character, Integer> frequencies) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(new HuffmanComparator());

        // Create a leaf node for each character and add it to the priority queue
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getValue(), entry.getKey()));
        }

        // Build the tree by combining the two nodes with the lowest frequency
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            int sum = left.frequency + right.frequency;
            priorityQueue.add(new HuffmanNode(sum, left, right));
        }

        // Set the root of the tree
        root = priorityQueue.poll();

        // Generate Huffman codes for each character
        huffmanCodes = new HashMap<>();
        generateCodes(root, "");
    }

    /**
     * Recursively generates Huffman codes for each character in the tree.
     *
     * @param node The current node in the tree.
     * @param code The Huffman code generated so far.
     */
    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) {
            return;
        }

        if (node.isLeaf) {
            huffmanCodes.put(node.character, code);
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    /**
     * Returns the root node of the Huffman tree.
     *
     * @return The root node.
     */
    public HuffmanNode getRoot() {
        return root;
    }

    /**
     * Returns the map of characters to their Huffman codes.
     *
     * @return A map of characters and their corresponding Huffman codes.
     */
    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    /**
     * Sets the root node of the Huffman tree.
     *
     * @param root The root node to set.
     */
    public void setRoot(HuffmanNode root) {
        this.root = root;
    }
}