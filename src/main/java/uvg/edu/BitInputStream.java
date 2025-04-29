package uvg.edu;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

class BitInputStream implements AutoCloseable {
    private InputStream in;
    private int currentByte;
    private int bitPosition;

    public BitInputStream(InputStream in) {
        this.in = in;
        this.bitPosition = 8;
    }

    public boolean readBit() throws IOException {
        if (bitPosition == 8) {
            currentByte = in.read();
            if (currentByte == -1) {
                throw new EOFException();
            }
            bitPosition = 0;
        }

        boolean bit = ((currentByte >> (7 - bitPosition)) & 1) == 1;
        bitPosition++;
        return bit;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}