package uvg.edu;

import java.io.IOException;
import java.io.OutputStream;

/**
 * BitOutputStream class provides functionality to write individual bits to an output stream.
 * It maintains the current byte being written and the position of the bit within that byte.
 * Implements AutoCloseable to ensure the output stream is properly closed.
 */
class BitOutputStream implements AutoCloseable {
    private OutputStream out; // The underlying output stream
    private int currentByte; // The current byte being constructed
    private int bitPosition; // The position of the next bit to write in the current byte

    /**
     * Constructs a BitOutputStream with the specified output stream.
     *
     * @param out The output stream to write bits to.
     */
    public BitOutputStream(OutputStream out) {
        this.out = out;
        this.currentByte = 0; // Initialize the current byte to 0
        this.bitPosition = 0; // Initialize the bit position to 0
    }

    /**
     * Writes a single bit to the output stream.
     *
     * @param bit The bit to write (true for 1, false for 0).
     * @throws IOException If an I/O error occurs while writing to the stream.
     */
    public void writeBit(boolean bit) throws IOException {
        if (bit) {
            // Set the bit at the current position in the current byte
            currentByte = currentByte | (1 << (7 - bitPosition));
        }

        bitPosition++; // Move to the next bit position

        if (bitPosition == 8) { // If the current byte is full
            out.write(currentByte); // Write the byte to the output stream
            currentByte = 0; // Reset the current byte
            bitPosition = 0; // Reset the bit position
        }
    }

    /**
     * Closes the output stream.
     * If there are remaining bits in the current byte, it writes the byte before closing.
     *
     * @throws IOException If an I/O error occurs while closing the stream.
     */
    @Override
    public void close() throws IOException {
        if (bitPosition > 0) { // If there are unwritten bits in the current byte
            out.write(currentByte); // Write the remaining bits
        }
        out.close(); // Close the underlying output stream
    }
}