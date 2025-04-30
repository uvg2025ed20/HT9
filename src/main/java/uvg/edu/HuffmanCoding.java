package uvg.edu;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HuffmanCoding {

    private static Scanner scanner = new Scanner(System.in);

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

    public static void compressMenu() {
        String inputFile = "src/main/java/uvg/edu/text.txt"; // Ruta del archivo a comprimir
        String outputFile = "src/main/java/uvg/edu/compress/text"; // Prefijo para los archivos comprimidos

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
            System.out.println("Archivo comprimido guardado en: " + outputFile + ".huff");
            System.out.println("Árbol de Huffman guardado en: " + outputFile + ".hufftree");

        } catch (IOException e) {
            System.err.println("\nError durante la compresión: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void decompressMenu() {
        String huffFile = "src/main/java/uvg/edu/compress/text.huff"; // Ruta del archivo comprimido
        String treeFile = "src/main/java/uvg/edu/compress/text.hufftree"; // Ruta del archivo del árbol
        String outputFile = "src/main/java/uvg/edu/output/textDescompressed.txt"; // Ruta del archivo descomprimido

        try {
            HuffmanDecompressor decompressor = new HuffmanDecompressor();
            HuffmanTree tree = decompressor.loadHuffmanTree(treeFile);

            String decompressedText = decompressor.decompress(huffFile, tree.getRoot());

            Files.write(Paths.get(outputFile), decompressedText.getBytes());

            System.out.println("\nDescompresión completada exitosamente!");
            System.out.println("Archivo descomprimido guardado en: " + outputFile);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\nError durante la descompresión: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

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