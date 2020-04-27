package net.jhaveri;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GiphyTunnelServer {
    private ServerSocket server;
    private static String GIPHY_API_IP = "151.101.54.2";
    private static int port = 8443;

    private static String GIPHY_HOST = "api.giphy.com";
    private static int GIPHY_PORT = 443;

    public static void main(String[] args) throws IOException {
        System.out.println("running");
        listen();
        System.out.println("Finished");
    }

    public static void listen() {
        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                InputStream clientIn = clientSocket.getInputStream();
                OutputStream clientOut = clientSocket.getOutputStream();
                Socket giphySocket = new Socket(GIPHY_HOST, GIPHY_PORT);
                InputStream giphyIn = giphySocket.getInputStream();
                OutputStream giphyOut = giphySocket.getOutputStream();
                //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            SocketPipe clientToGiphy = new SocketPipe("client->giph", clientIn, giphyOut);
            SocketPipe giphyToClient = new SocketPipe("giph->client", giphyIn, clientOut);
            clientToGiphy.start();
            giphyToClient.start();

            clientToGiphy.join();
            giphyToClient.join();
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + port + " or listening for a connection");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class SocketPipe extends Thread {
        private InputStream in;
        private OutputStream out;
        private byte[] buff = new byte[1024];

        public SocketPipe(String name, InputStream in, OutputStream out) {
            super(name);
            this.in = in;
            this.out = out;
        }

        @Override
        public void run() {
            int len = 0;
            while (len != -1) {
                try {
                    len = in.read(buff);
                    System.out.println("read " + len + " from client");
                    out.write(buff, 0, len);
                    System.out.println("write to giph");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
