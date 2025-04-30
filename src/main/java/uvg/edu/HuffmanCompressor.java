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

        /**
         * HuffmanCompressor class provides methods to calculate character frequencies,
         * compress text using Huffman codes, write the compressed text to a file, and
         * save the Huffman tree to a file.
         */
        class HuffmanCompressor {

            /**
             * Calculates the frequency of each character in the given text.
             *
             * @param text The input text for which character frequencies are calculated.
             * @return A map containing characters as keys and their frequencies as values.
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
             * @param text         The input text to be compressed.
             * @param huffmanCodes A map containing characters as keys and their corresponding Huffman codes as values.
             * @return A string representing the compressed text.
             */
            public String compress(String text, Map<Character, String> huffmanCodes) {
                StringBuilder compressedText = new StringBuilder();

                for (char c : text.toCharArray()) {
                    compressedText.append(huffmanCodes.get(c));
                }

                return compressedText.toString();
            }

            /**
             * Writes the compressed text to a file at the specified output path.
             *
             * @param compressedText The compressed text to be written to the file.
             * @param outputPath     The path of the file where the compressed text will be saved.
             * @throws IOException If an I/O error occurs while writing to the file.
             */
            public void writeCompressedFile(String compressedText, String outputPath) throws IOException {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
                    writer.write(compressedText);
                }
            }

            /**
             * Saves the Huffman tree to a file at the specified output path.
             *
             * @param tree       The Huffman tree to be saved.
             * @param outputPath The path of the file where the Huffman tree will be saved.
             * @throws IOException If an I/O error occurs while writing to the file.
             */
            public void saveHuffmanTree(HuffmanTree tree, String outputPath) throws IOException {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputPath))) {
                    oos.writeObject(tree);
                }
            }
        }