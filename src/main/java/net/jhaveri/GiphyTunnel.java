package net.jhaveri;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class GiphyTunnel extends Thread {
    private final static String GIPHY_HOST = "api.giphy.com";
    private final static int GIPHY_PORT = 443;
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
            SocketPipe clientToGiphy = new SocketPipe("client->giph", clientIn, giphyOut);
            SocketPipe giphyToClient = new SocketPipe("giph->client", giphyIn, clientOut);

            clientToGiphy.start();
            giphyToClient.start();

            clientToGiphy.join();
            giphyToClient.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
