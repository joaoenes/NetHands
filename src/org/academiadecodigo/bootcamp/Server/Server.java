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
    private List<Client> listOfClients;
    private List<Client> listOfGames;

    public Server() {
        cachedPool = Executors.newCachedThreadPool();
        listOfClients = new LinkedList<>();
        listOfGames = new LinkedList<>();
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

    public void init() {
        try {
            System.out.print("PORT: ");
            serverSocket = new ServerSocket(sacanner());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPlayers(){
        return listOfClients.size() % 2 == 0;
    }

    private void startGame(Client client1, Client client2){
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                Game game = new Game(client1, client2);
                game.run();
            }
        });
    }

    private int sacanner() {
        Scanner scanner = new Scanner(System.in);
        int port = Integer.parseInt(scanner.nextLine());
        return port;
    }

    private void newThread(Client client) {
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                client.run();
            }
        });
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.run();
    }
}
