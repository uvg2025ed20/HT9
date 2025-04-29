package uvg.edu;

import java.io.IOException;
import java.io.OutputStream;

class BitOutputStream implements AutoCloseable {
    private OutputStream out;
    private int currentByte;
    private int bitPosition;

    public BitOutputStream(OutputStream out) {
        this.out = out;
        this.currentByte = 0;
        this.bitPosition = 0;
    }

    public void writeBit(boolean bit) throws IOException {
        if (bit) {
            currentByte = currentByte | (1 << (7 - bitPosition));
        }

        bitPosition++;

        if (bitPosition == 8) {
            out.write(currentByte);
            currentByte = 0;
            bitPosition = 0;
        }
    }

    @Override
    public void close() throws IOException {
        if (bitPosition > 0) {
            out.write(currentByte);
        }
        out.close();
    }
}