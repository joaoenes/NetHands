package org.academiadecodigo.bootcamp.Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static GameHandler gameHandler;
    private ServerSocket serverSocket;
    private ExecutorService cachedPool;
    private Socket clientSocket;
    private List<Game> listOfGames;

    public Server() {
        cachedPool = Executors.newCachedThreadPool();
        listOfGames = new LinkedList<>();
        gameHandler = new GameHandler();
    }

    public void init() {
        try {
            System.out.print("PORT: ");
            serverSocket = new ServerSocket(sacanner());
        } catch (IOException  e) {
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

    public void run() {
        int counter = 0;
        String clientName = "";

        while (true) {

            try {

                clientName = "Guess" + ++counter;
                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientName, clientSocket);
                newThread(clientHandler);

                //clientJoin(clientHandler1, clientHandler2);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void joinGame(ClientHandler client){
        gameHandler.clientJoin(client);
    }

    public static void saveLog(String buffer) {

        synchronized (Game.class) {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream("saveLog.txt");
                outputStream.write(buffer.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int sacanner() {
        Scanner scanner = new Scanner(System.in);
        int port = Integer.parseInt(scanner.nextLine());
        return port;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.run();
    }
}
