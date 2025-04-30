package uvg.edu;

        import java.io.EOFException;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;

        /**
         * HuffmanDecompressor class provides methods to load a Huffman tree from a file
         * and decompress a file using the Huffman tree.
         */
        class HuffmanDecompressor {

            /**
             * Loads a Huffman tree from a file.
             *
             * @param treeFile The path to the file containing the serialized Huffman tree.
             * @return The deserialized HuffmanTree object.
             * @throws IOException            If an I/O error occurs while reading the file.
             * @throws ClassNotFoundException If the class of the serialized object cannot be found.
             */
            public HuffmanTree loadHuffmanTree(String treeFile) throws IOException, ClassNotFoundException {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(treeFile))) {
                    return (HuffmanTree) in.readObject();
                }
            }

            /**
             * Decompresses a file using the provided Huffman tree root node.
             *
             * @param compressedFile The path to the compressed file.
             * @param root           The root node of the Huffman tree used for decompression.
             * @return The decompressed text as a string.
             * @throws IOException If an I/O error occurs while reading the compressed file.
             */
            public String decompress(String compressedFile, HuffmanNode root) throws IOException {
                StringBuilder decompressed = new StringBuilder();
                HuffmanNode current = root;

                try (BitInputStream in = new BitInputStream(new FileInputStream(compressedFile))) {
                    while (true) {
                        try {
                            // Read the next bit from the compressed file
                            boolean bit = in.readBit();

                            // Traverse the Huffman tree based on the bit value
                            if (bit) {
                                current = current.right;
                            } else {
                                current = current.left;
                            }

                            // If a leaf node is reached, append the character to the result
                            if (current.isLeaf) {
                                decompressed.append(current.character);
                                current = root; // Reset to the root for the next character
                            }
                        } catch (EOFException e) {
                            // End of file reached
                            break;
                        }
                    }
                }

                return decompressed.toString();
            }
        }