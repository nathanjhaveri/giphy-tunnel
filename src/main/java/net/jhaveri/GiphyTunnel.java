package net.jhaveri;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class GiphyTunnel implements Runnable {
    private final static String GIPHY_HOST = "api.giphy.com";
    private final static int GIPHY_PORT = 443;
    private final static int MAX_TIMEOUT_MILLIS = 5000;
    private Socket clientSocket;

    public GiphyTunnel(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                Socket socket = this.clientSocket; // Make sure socket gets cleaned up
                InputStream clientIn = socket.getInputStream();
                OutputStream clientOut = socket.getOutputStream();
                Socket giphySocket = new Socket(GIPHY_HOST, GIPHY_PORT);
                InputStream giphyIn = giphySocket.getInputStream();
                OutputStream giphyOut = giphySocket.getOutputStream();
        ) {
            socket.setSoTimeout(MAX_TIMEOUT_MILLIS);
            giphySocket.setSoTimeout(MAX_TIMEOUT_MILLIS);
            SocketPipe clientToGiphy = new SocketPipe("client->giphy", clientIn, giphyOut);
            SocketPipe giphyToClient = new SocketPipe("giphy->client", giphyIn, clientOut);

            clientToGiphy.start();
            giphyToClient.start();

            clientToGiphy.join();
            giphyToClient.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
