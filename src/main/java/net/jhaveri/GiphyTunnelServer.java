package net.jhaveri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GiphyTunnelServer {
    private final static int PORT = 8443;
    private final static int SIMULTANEOUS_REQUEST_LIMIT = 10;

    public static void main(String[] args) {
        listen();
        System.out.println("Finished");
    }

    public static void listen() {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                SIMULTANEOUS_REQUEST_LIMIT,
                SIMULTANEOUS_REQUEST_LIMIT,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on " + serverSocket.getLocalSocketAddress());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                if (executorService.getQueue().size() < SIMULTANEOUS_REQUEST_LIMIT) {
                    GiphyTunnel giphyTunnel = new GiphyTunnel(clientSocket);
                    executorService.execute(giphyTunnel);
                } else {
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        executorService.shutdown();
    }
}
