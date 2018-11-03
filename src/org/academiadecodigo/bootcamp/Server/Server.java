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

    private ServerSocket serverSocket;
    private ExecutorService cachedPool;
    private Socket clientSocket1;
    private Socket clientSocket2;
    private List<Game> listOfGames;

    public Server() {
        cachedPool = Executors.newCachedThreadPool();
        listOfGames = new LinkedList<>();
    }

    public void init() {
        try {
            System.out.print("PORT: ");
            serverSocket = new ServerSocket(sacanner());
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    private void startGame(ClientHandler clientHandler1, ClientHandler clientHandler2) {
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                Game game = new Game(clientHandler1, clientHandler2);
                listOfGames.add(game);
                game.start();
            }
        });
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
                clientSocket1 = serverSocket.accept();
                ClientHandler clientHandler1 = new ClientHandler(clientName, clientSocket1);

                clientName = "Guess" + ++counter;
                clientSocket2 = serverSocket.accept();
                ClientHandler clientHandler2 = new ClientHandler(clientName, clientSocket2);


                newThread(clientHandler1);
                newThread(clientHandler2);

                startGame(clientHandler1, clientHandler2);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
