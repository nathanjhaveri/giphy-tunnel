package net.jhaveri;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SocketPipe extends Thread {
    private String name;
    private InputStream in;
    private OutputStream out;
    private byte[] buff = new byte[1024 * 64];

    public SocketPipe(String name, InputStream in, OutputStream out) {
        super();
        this.name = name;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            int len = in.read(buff);
            while (len != -1) {
                out.write(buff, 0, len);
                len = in.read(buff);
            }

            System.out.println(name + " transfer complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
