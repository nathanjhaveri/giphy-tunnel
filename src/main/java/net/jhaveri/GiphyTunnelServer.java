package net.jhaveri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GiphyTunnelServer {
    private final static int PORT = 8443;

    public static void main(String[] args) {
        listen();
        System.out.println("Finished");
    }

    public static void listen() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on " + serverSocket.getLocalSocketAddress());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                GiphyTunnel giphyTunnel = new GiphyTunnel(clientSocket);
                giphyTunnel.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
