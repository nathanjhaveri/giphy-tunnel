package net.jhaveri;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SocketPipe extends Thread {
    public final static int BUFF_SIZE = 1024 * 64; // Experimentally more than enough room for a given read. Typical MTU is only 1500
    private String name;
    private InputStream in;
    private OutputStream out;
    private byte[] buff = new byte[BUFF_SIZE];

    public SocketPipe(String name, InputStream in, OutputStream out) {
        super();
        this.name = name;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        pipeInputToOutput();
    }

    public void pipeInputToOutput() {
        try {
            int len = in.read(buff);
            while (len != -1) {
                out.write(buff, 0, len);
                len = in.read(buff);
            }

            System.out.println(name + " transfer complete");
        } catch (IOException e) {
            System.err.println(name + " " + e.getMessage());
            e.printStackTrace();
        }
    }
}
