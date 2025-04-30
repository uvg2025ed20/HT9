package uvg.edu;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        StringBuilder compressedText = new StringBuilder();
    
        for (char c : text.toCharArray()) {
            compressedText.append(huffmanCodes.get(c));
        }
    
        return compressedText.toString();
    }

    public void writeCompressedFile(String compressedText, String outputPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(compressedText);
        }
    }

    public void saveHuffmanTree(HuffmanTree tree, String outputPath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputPath))) {
            oos.writeObject(tree);
        }
    }
}