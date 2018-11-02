package org.academiadecodigo.bootcamp.Server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame(Client client1, Client client2) {
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                Game game = new Game(client1, client2);
                listOfGames.add(game);
                game.start();
            }
        });
    }

    private void newThread(Client client) {
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                client.run();
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
                Client client1 = new Client(clientName, clientSocket1);

                clientName = "Guess" + ++counter;
                clientSocket2 = serverSocket.accept();
                Client client2 = new Client(clientName, clientSocket2);


                newThread(client1);
                newThread(client2);

                startGame(client1, client2);

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
