package uvg.edu;

import java.io.*;
import java.util.*;

class HuffmanNode implements Serializable {
    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;
    boolean isLeaf;

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
        this.isLeaf = false;
    }

    public HuffmanNode(int frequency, char character) {
        this.frequency = frequency;
        this.character = character;
        this.left = null;
        this.right = null;
        this.isLeaf = true;
    }
}

class HuffmanComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return node1.frequency - node2.frequency;
    }
}

class HuffmanTree implements Serializable {
    private HuffmanNode root;
    private Map<Character, String> huffmanCodes;

    public void buildTree(Map<Character, Integer> frequencies) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(new HuffmanComparator());

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getValue(), entry.getKey()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            int sum = left.frequency + right.frequency;
            priorityQueue.add(new HuffmanNode(sum, left, right));
        }

        root = priorityQueue.poll();

        huffmanCodes = new HashMap<>();
        generateCodes(root, "");
    }

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

    public HuffmanNode getRoot() {
        return root;
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    public void setRoot(HuffmanNode root) {
        this.root = root;
    }
}