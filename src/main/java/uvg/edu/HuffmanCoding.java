package uvg.edu;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Main class for compressing and decompressing files using the Huffman algorithm.
 */
public class HuffmanCoding {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Compresses a text file using the Huffman algorithm.
     *
     * @param inputFile  The path to the input file to be compressed.
     * @param outputFile The base path for the generated compressed files.
     */
    public static void compress(String inputFile, String outputFile) {
        try {
            String text = new String(Files.readAllBytes(Paths.get(inputFile)));

            HuffmanCompressor compressor = new HuffmanCompressor();
            Map<Character, Integer> frequencies = compressor.calculateFrequencies(text);

            HuffmanTree tree = new HuffmanTree();
            tree.buildTree(frequencies);

            Map<Character, String> huffmanCodes = tree.getHuffmanCodes();

            String compressedText = compressor.compress(text, huffmanCodes);

            compressor.writeCompressedFile(compressedText, outputFile + ".huff");
            compressor.saveHuffmanTree(tree, outputFile + ".hufftree");

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
     * Decompresses a file using the Huffman tree.
     *
     * @param inputFile  The path to the compressed file (.huff).
     * @param treeFile   The path to the Huffman tree file (.hufftree).
     * @param outputFile The path to the output decompressed file.
     */
    public static void decompress(String inputFile, String treeFile, String outputFile) {
        try {
            HuffmanDecompressor decompressor = new HuffmanDecompressor();
            HuffmanTree tree = decompressor.loadHuffmanTree(treeFile);

            String decompressedText = decompressor.decompress(inputFile, tree.getRoot());

            Files.write(Paths.get(outputFile), decompressedText.getBytes());

            System.out.println("\nDescompresión completada exitosamente!");
            System.out.println("El archivo descomprimido se ha guardado como: " + outputFile);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\nError durante la descompresión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays the main menu for the user to select an option.
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

    private static final String DEFAULT_OUTPUT_DIRECTORY = "src\\main\\java\\uvg\\edu\\datos\\";

    /**
     * Generates the full path for a file in the default output directory.
     *
     * @param fileName The name of the file.
     * @return The full path of the file in the default directory.
     */
    private static String getOutputPath(String fileName) {
        File directory = new File(DEFAULT_OUTPUT_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it does not exist
        }
        return DEFAULT_OUTPUT_DIRECTORY + fileName;
    }

    /**
     * Displays the menu for compressing files.
     */
    private static void compressMenu() {
        System.out.println("\n== COMPRESIÓN DE ARCHIVO ==");
        System.out.print("Ingrese la ruta del archivo a comprimir (ej: texto.txt): ");
        String inputFile = scanner.nextLine();

        if (!fileExists(inputFile)) {
            System.out.println("Error: El archivo especificado no existe.");
            return;
        }

        System.out.print("Ingrese el nombre base para los archivos comprimidos (sin extensión): ");
        String outputPrefix = scanner.nextLine();

        String outputPath = getOutputPath(outputPrefix); // Use the default path
        System.out.println("\nComprimiendo archivo...");
        compress(inputFile, outputPath);
    }

    /**
     * Displays the menu for decompressing files.
     */
    private static void decompressMenu() {
        System.out.println("\n== DESCOMPRESIÓN DE ARCHIVO ==");
        System.out.print("Ingrese la ruta del archivo comprimido (.huff): ");
        String huffFile = scanner.nextLine();

        if (!fileExists(huffFile)) {
            System.out.println("Error: El archivo .huff especificado no existe.");
            return;
        }

        System.out.print("Ingrese la ruta del archivo de árbol (.hufftree): ");
        String treeFile = scanner.nextLine();

        if (!fileExists(treeFile)) {
            System.out.println("Error: El archivo .hufftree especificado no existe.");
            return;
        }

        System.out.print("Ingrese el nombre para el archivo descomprimido: ");
        String outputFile = scanner.nextLine();

        String outputPath = getOutputPath(outputFile); // Use the default path
        System.out.println("\nDescomprimiendo archivo...");
        decompress(huffFile, treeFile, outputPath);
    }

    /**
     * Checks if a file exists at the specified path.
     *
     * @param filePath The path of the file.
     * @return {@code true} if the file exists, {@code false} otherwise.
     */
    private static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Main method that executes the program.
     *
     * @param args Command-line arguments:
     *             - "-c <input_file> <output_prefix>": Compress a file.
     *             - "-d <compressed_file> <tree_file> <output_file>": Decompress a file.
     *             If no arguments are provided, the program displays an interactive menu.
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