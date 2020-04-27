package net.jhaveri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GiphyTunnelServer {
    private ServerSocket server;
    private final static int PORT = 8443;

    public static void main(String[] args) {
        System.out.println("running");
        listen();
        System.out.println("Finished");
    }

    public static void listen() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                GiphyTunnel giphyTunnel = new GiphyTunnel(clientSocket);
                giphyTunnel.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
