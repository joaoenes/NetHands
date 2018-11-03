package org.academiadecodigo.bootcamp.Server;

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
    private List<ClientHandler> listOfClientHandlers;
    private List<ClientHandler> listOfGames;

    public Server() {
        cachedPool = Executors.newCachedThreadPool();
        listOfClientHandlers = new LinkedList<>();
        listOfGames = new LinkedList<>();
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

    public void init() {
        try {
            System.out.print("PORT: ");
            serverSocket = new ServerSocket(scanner());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPlayers(){
        return listOfClientHandlers.size() % 2 == 0;
    }

    private void startGame(ClientHandler clientHandler1, ClientHandler clientHandler2){
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                Game game = new Game(clientHandler1, clientHandler2);
                game.start();
            }
        });
    }

    private int scanner() {
        Scanner scanner = new Scanner(System.in);
        int port = Integer.parseInt(scanner.nextLine());
        return port;
    }

    private void newThread(ClientHandler clientHandler) {
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                clientHandler.run();
            }
        });
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.run();
    }
}
