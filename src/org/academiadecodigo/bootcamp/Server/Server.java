package org.academiadecodigo.bootcamp.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService cachedPool;
    private Socket clientSocket;

    public Server() {
        cachedPool = Executors.newCachedThreadPool();
    }

    public void run() {
        while (true) {

            try {
                
                clientSocket = serverSocket.accept();
                newThread(clientSocket);
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void init() {
        try {
            System.out.print("PORT: ");
            serverSocket = new ServerSocket(sacanner());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int sacanner() {
        Scanner scanner = new Scanner(System.in);
        int port = Integer.parseInt(scanner.nextLine());
        return port;
    }

    private void newThread(Socket clientSocket) {
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                new Client(clientSocket);
            }
        });
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.run();
    }
}
