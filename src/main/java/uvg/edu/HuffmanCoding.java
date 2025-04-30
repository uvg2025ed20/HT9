package uvg.edu;

            import java.io.*;
            import java.nio.file.Files;
            import java.nio.file.Paths;
            import java.util.*;

            /**
             * HuffmanCoding class provides methods to compress and decompress files using Huffman coding.
             * It includes a menu-driven interface for user interaction and command-line options for automation.
             */
            public class HuffmanCoding {

                // Scanner for user input in the menu
                private static Scanner scanner = new Scanner(System.in);

                /**
                 * Compresses a file using Huffman coding.
                 *
                 * @param inputFile  The path to the input file to be compressed.
                 * @param outputFile The prefix for the output compressed file and Huffman tree file.
                 */
                public static void compress(String inputFile, String outputFile) {
                    try {
                        // Read the content of the input file
                        String text = new String(Files.readAllBytes(Paths.get(inputFile)));

                        // Calculate character frequencies
                        HuffmanCompressor compressor = new HuffmanCompressor();
                        Map<Character, Integer> frequencies = compressor.calculateFrequencies(text);

                        // Build the Huffman tree
                        HuffmanTree tree = new HuffmanTree();
                        tree.buildTree(frequencies);

                        // Generate Huffman codes
                        Map<Character, String> huffmanCodes = tree.getHuffmanCodes();

                        // Compress the text
                        String compressedText = compressor.compress(text, huffmanCodes);

                        // Write the compressed file and save the Huffman tree
                        compressor.writeCompressedFile(compressedText, outputFile + ".huff");
                        compressor.saveHuffmanTree(tree, outputFile + ".hufftree");

                        // Display compression statistics
                        System.out.println("\nCompresión completada exitosamente!");
                        System.out.println("Tamaño original: " + text.length() * 8 + " bits");
                        System.out.println("Tamaño comprimido: " + compressedText.length() + " bits");
                        double ratio = (double) compressedText.length() / (text.length() * 8) * 100;
                        System.out.println("Ratio de compresión: " + String.format("%.2f", ratio) + "%");

                    } catch (IOException e) {
                        System.err.println("\nError durante la compresión: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                /**
                 * Decompresses a file using a saved Huffman tree.
                 *
                 * @param inputFile  The path to the compressed file.
                 * @param treeFile   The path to the file containing the saved Huffman tree.
                 * @param outputFile The path to save the decompressed file.
                 */
                public static void decompress(String inputFile, String treeFile, String outputFile) {
                    try {
                        // Load the Huffman tree
                        HuffmanDecompressor decompressor = new HuffmanDecompressor();
                        HuffmanTree tree = decompressor.loadHuffmanTree(treeFile);

                        // Decompress the file
                        String decompressedText = decompressor.decompress(inputFile, tree.getRoot());

                        // Write the decompressed text to the output file
                        Files.write(Paths.get(outputFile), decompressedText.getBytes());

                        System.out.println("\nDescompresión completada exitosamente!");
                        System.out.println("El archivo descomprimido se ha guardado como: " + outputFile);

                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("\nError durante la descompresión: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                /**
                 * Displays the main menu for the Huffman compressor.
                 * Allows the user to choose between compression, decompression, or exiting the program.
                 */
                public static void showMenu() {
                    boolean exit = false;

                    while (!exit) {
                        System.out.println("\n=== COMPRESOR DE HUFFMAN ===");
                        System.out.println("1. Comprimir un archivo");
                        System.out.println("2. Descomprimir un archivo");
                        System.out.println("3. Salir");
                        System.out.print("Seleccione una opción: ");

                        try {
                            int option = Integer.parseInt(scanner.nextLine());

                            switch (option) {
                                case 1:
                                    compressMenu();
                                    break;
                                case 2:
                                    decompressMenu();
                                    break;
                                case 3:
                                    exit = true;
                                    System.out.println("¡Gracias por usar el Compresor de Huffman!");
                                    break;
                                default:
                                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, ingrese un número válido.");
                        }
                    }
                }

                /**
                 * Handles the compression menu option.
                 * Uses predefined file paths for input and output.
                 */
                public static void compressMenu() {
                    String inputFile = "src/main/java/uvg/edu/text.txt"; // Path to the file to compress
                    String outputFile = "src/main/java/uvg/edu/compress/text"; // Prefix for compressed files

                    try {
                        // Read the content of the input file
                        String text = new String(Files.readAllBytes(Paths.get(inputFile)));

                        // Calculate character frequencies
                        HuffmanCompressor compressor = new HuffmanCompressor();
                        Map<Character, Integer> frequencies = compressor.calculateFrequencies(text);

                        // Build the Huffman tree
                        HuffmanTree tree = new HuffmanTree();
                        tree.buildTree(frequencies);

                        // Generate Huffman codes
                        Map<Character, String> huffmanCodes = tree.getHuffmanCodes();

                        // Compress the text
                        String compressedText = compressor.compress(text, huffmanCodes);

                        // Write the compressed file and save the Huffman tree
                        compressor.writeCompressedFile(compressedText, outputFile + ".huff");
                        compressor.saveHuffmanTree(tree, outputFile + ".hufftree");

                        System.out.println("\nCompresión completada exitosamente!");
                        System.out.println("Archivo comprimido guardado en: " + outputFile + ".huff");
                        System.out.println("Árbol de Huffman guardado en: " + outputFile + ".hufftree");

                    } catch (IOException e) {
                        System.err.println("\nError durante la compresión: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                /**
                 * Handles the decompression menu option.
                 * Uses predefined file paths for input and output.
                 */
                public static void decompressMenu() {
                    String huffFile = "src/main/java/uvg/edu/compress/text.huff"; // Path to the compressed file
                    String treeFile = "src/main/java/uvg/edu/compress/text.hufftree"; // Path to the Huffman tree file
                    String outputFile = "src/main/java/uvg/edu/output/textDescompressed.txt"; // Path to the decompressed file

                    try {
                        // Load the Huffman tree
                        HuffmanDecompressor decompressor = new HuffmanDecompressor();
                        HuffmanTree tree = decompressor.loadHuffmanTree(treeFile);

                        // Decompress the file
                        String decompressedText = decompressor.decompress(huffFile, tree.getRoot());

                        // Write the decompressed text to the output file
                        Files.write(Paths.get(outputFile), decompressedText.getBytes());

                        System.out.println("\nDescompresión completada exitosamente!");
                        System.out.println("Archivo descomprimido guardado en: " + outputFile);

                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("\nError durante la descompresión: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                /**
                 * Checks if a file exists at the given path.
                 *
                 * @param filePath The path to the file.
                 * @return True if the file exists, false otherwise.
                 */
                private static boolean fileExists(String filePath) {
                    return Files.exists(Paths.get(filePath));
                }

                /**
                 * Main method to run the HuffmanCoding program.
                 * Supports command-line arguments for compression and decompression, or displays a menu.
                 *
                 * @param args Command-line arguments.
                 */
                public static void main(String[] args) {
                    if (args.length > 0) {
                        String operation = args[0];

                        if (operation.equals("-c") && args.length == 3) {
                            compress(args[1], args[2]);
                        } else if (operation.equals("-d") && args.length == 4) {
                            decompress(args[1], args[2], args[3]);
                        } else {
                            System.out.println("Uso:");
                            System.out.println("  Para comprimir: java HuffmanCoding -c <archivo_entrada> <prefijo_salida>");
                            System.out.println(
                                    "  Para descomprimir: java HuffmanCoding -d <archivo.huff> <archivo.hufftree> <archivo_salida>");
                        }
                    } else {
                        showMenu();
                    }
                }
            }