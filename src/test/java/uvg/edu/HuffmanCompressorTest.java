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

        assertEquals(3, (int) frequencies.get('a'));
        assertEquals(2, (int) frequencies.get('b'));
        assertEquals(1, (int) frequencies.get('c'));
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

        assertEquals("01011", compressedText);
    }

    @Test
    public void testWriteCompressedFile() throws IOException {
        HuffmanCompressor compressor = new HuffmanCompressor();
        String compressedText = "01011";
        String outputPath = "test_compressed.huff";

        compressor.writeCompressedFile(compressedText, outputPath);

        File file = new File(outputPath);
        assertTrue(file.exists());

        String fileContent = new String(Files.readAllBytes(Paths.get(outputPath)));
        assertEquals(compressedText, fileContent);

        // Cleanup
        file.delete();
    }
}