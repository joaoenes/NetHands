package org.academiadecodigo.bootcamp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static GameHandler gameHandler;
    private ServerSocket serverSocket;
    private ExecutorService cachedPool;

    private Server() {
        cachedPool = Executors.newCachedThreadPool();
        gameHandler = new GameHandler();
    }

    private void init() {
        try {
            System.out.print("PORT: ");
            serverSocket = new ServerSocket(scanner());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newThread(ClientHandler clientHandler) {
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                clientHandler.run();
            }
        });
    }

    static void joinGame(ClientHandler client) {
        gameHandler.clientJoin(client);
    }

    private void run() {
        int counter = 0;
        String clientName = "";

        while (true) {

            try {
                clientName = "Guest" + ++counter;
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientName, clientSocket);
                newThread(clientHandler);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int scanner() {
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(scanner.nextLine());
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.run();
    }
}
