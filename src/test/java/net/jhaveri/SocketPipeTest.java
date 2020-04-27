package net.jhaveri;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SocketPipeTest {
    @Test
    public void sendsInputToOutput() {
        String expected = "input";
        InputStream in = new ByteArrayInputStream(expected.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SocketPipe socketPipe = new SocketPipe("", in, out);

        socketPipe.pipeInputToOutput();
        String actual = new String(out.toByteArray());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void pipesLargeInput() {
        String expected = "A string that is longer than a byte".repeat(SocketPipe.BUFF_SIZE);
        InputStream in = new ByteArrayInputStream(expected.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SocketPipe socketPipe = new SocketPipe("", in, out);

        socketPipe.pipeInputToOutput();
        String actual = new String(out.toByteArray());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handlesEmptyInput() {
        byte[] expected = new byte[0];
        InputStream in = new ByteArrayInputStream(expected);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SocketPipe socketPipe = new SocketPipe("", in, out);

        socketPipe.pipeInputToOutput();

        Assert.assertArrayEquals(expected, out.toByteArray());
    }
}