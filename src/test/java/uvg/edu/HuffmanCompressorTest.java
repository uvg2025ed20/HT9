package uvg.edu;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCompressorTest {

    @Test
    public void testCalculateFrequencies() {
        HuffmanCompressor compressor = new HuffmanCompressor();
        String text = "aaabbc";
        Map<Character, Integer> frequencies = compressor.calculateFrequencies(text);

        // Verificar las frecuencias de los caracteres
        assertEquals(3, (int) frequencies.get('a'));
        assertEquals(2, (int) frequencies.get('b'));
        assertEquals(1, (int) frequencies.get('c'));
        assertNull(frequencies.get('d')); // Verificar que un carácter no presente no esté en el mapa
    }

    @Test
    public void testCompress() {
        HuffmanCompressor compressor = new HuffmanCompressor();
        Map<Character, String> huffmanCodes = new HashMap<>();
        huffmanCodes.put('a', "0");
        huffmanCodes.put('b', "10");
        huffmanCodes.put('c', "11");

        String text = "abc";
        String compressedText = compressor.compress(text, huffmanCodes);

        // Verificar que el texto comprimido sea correcto
        assertEquals("01011", compressedText);
    }

    @Test
public void testWriteCompressedFileCreation() throws IOException {
    HuffmanCompressor compressor = new HuffmanCompressor();
    String compressedText = "01011";
    String outputPath = "test_compressed.huff";

    // Escribir el archivo comprimido
    compressor.writeCompressedFile(compressedText, outputPath);

    // Verificar que el archivo exista
    File file = new File(outputPath);
    assertTrue("El archivo no fue creado.", file.exists());

    // Cleanup: Eliminar el archivo después de la prueba
    if (file.exists()) {
        file.delete();
    }
}
}