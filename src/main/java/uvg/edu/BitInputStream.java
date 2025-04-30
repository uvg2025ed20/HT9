package uvg.edu;

    import java.io.EOFException;
    import java.io.IOException;
    import java.io.InputStream;

    /**
     * BitInputStream class provides functionality to read individual bits from an input stream.
     * It maintains the current byte being read and the position of the bit within that byte.
     * Implements AutoCloseable to ensure the input stream is properly closed.
     */
    class BitInputStream implements AutoCloseable {
        private InputStream in; // The underlying input stream
        private int currentByte; // The current byte being processed
        private int bitPosition; // The position of the next bit to read in the current byte

        /**
         * Constructs a BitInputStream with the specified input stream.
         *
         * @param in The input stream to read bits from.
         */
        public BitInputStream(InputStream in) {
            this.in = in;
            this.bitPosition = 8; // Initialize bit position to 8 (indicating a new byte needs to be read)
        }

        /**
         * Reads the next bit from the input stream.
         *
         * @return True if the bit is 1, false if the bit is 0.
         * @throws IOException  If an I/O error occurs while reading the stream.
         * @throws EOFException If the end of the stream is reached.
         */
        public boolean readBit() throws IOException {
            if (bitPosition == 8) { // If all bits in the current byte have been read
                currentByte = in.read(); // Read the next byte from the input stream
                if (currentByte == -1) { // Check for end of stream
                    throw new EOFException();
                }
                bitPosition = 0; // Reset bit position for the new byte
            }

            // Extract the bit at the current position
            boolean bit = ((currentByte >> (7 - bitPosition)) & 1) == 1;
            bitPosition++; // Move to the next bit
            return bit;
        }

        /**
         * Closes the input stream.
         *
         * @throws IOException If an I/O error occurs while closing the stream.
         */
        @Override
        public void close() throws IOException {
            in.close();
        }
    }