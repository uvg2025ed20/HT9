package uvg.edu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for compressing text using the Huffman algorithm.
 * It provides methods to calculate character frequencies, compress text,
 * write compressed files, and save the Huffman tree.
 */
class HuffmanCompressor {

    /**
     * Calculates the frequency of each character in the given text.
     *
     * @param text The input text to analyze.
     * @return A map where the keys are characters and the values are their frequencies.
     */
    public Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();

        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }

        return frequencies;
    }

    /**
     * Compresses the given text using the provided Huffman codes.
     *
     * @param text         The input text to compress.
     * @param huffmanCodes A map where the keys are characters and the values are their Huffman codes.
     * @return A string representing the compressed text as a sequence of bits.
     */
    public String compress(String text, Map<Character, String> huffmanCodes) {
        StringBuilder compressed = new StringBuilder();

        for (char c : text.toCharArray()) {
            compressed.append(huffmanCodes.get(c));
        }

        return compressed.toString();
    }

    /**
     * Writes the compressed text to a file as a sequence of bits.
     *
     * @param compressedText The compressed text represented as a string of '0's and '1's.
     * @param outputFile      The path to the output file where the compressed data will be written.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeCompressedFile(String compressedText, String outputFile) throws IOException {
        try (BitOutputStream out = new BitOutputStream(new FileOutputStream(outputFile))) {
            for (char bit : compressedText.toCharArray()) {
                out.writeBit(bit == '1');
            }
        }
    }

    /**
     * Saves the Huffman tree to a file by serializing it.
     *
     * @param tree       The Huffman tree to save.
     * @param outputFile The path to the output file where the tree will be serialized.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void saveHuffmanTree(HuffmanTree tree, String outputFile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            out.writeObject(tree);
        }
    }
}