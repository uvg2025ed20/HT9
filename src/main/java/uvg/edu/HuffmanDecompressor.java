package uvg.edu;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

class HuffmanDecompressor {

    public HuffmanTree loadHuffmanTree(String treeFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(treeFile))) {
            return (HuffmanTree) in.readObject();
        }
    }

    public String decompress(String compressedFile, HuffmanNode root) throws IOException {
        StringBuilder decompressed = new StringBuilder();
        HuffmanNode current = root;

        try (BitInputStream in = new BitInputStream(new FileInputStream(compressedFile))) {
            while (true) {
                try {
                    boolean bit = in.readBit();

                    if (bit) {
                        current = current.right;
                    } else {
                        current = current.left;
                    }

                    if (current.isLeaf) {
                        decompressed.append(current.character);
                        current = root;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        }

        return decompressed.toString();
    }
}
