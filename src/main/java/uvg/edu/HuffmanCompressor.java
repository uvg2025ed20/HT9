package uvg.edu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

class HuffmanCompressor {

    public Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();

        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }

        return frequencies;
    }

    public String compress(String text, Map<Character, String> huffmanCodes) {
        StringBuilder compressed = new StringBuilder();

        for (char c : text.toCharArray()) {
            compressed.append(huffmanCodes.get(c));
        }

        return compressed.toString();
    }

    public void writeCompressedFile(String compressedText, String outputFile) throws IOException {
        try (BitOutputStream out = new BitOutputStream(new FileOutputStream(outputFile))) {
            for (char bit : compressedText.toCharArray()) {
                out.writeBit(bit == '1');
            }
        }
    }

    public void saveHuffmanTree(HuffmanTree tree, String outputFile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            out.writeObject(tree);
        }
    }
}