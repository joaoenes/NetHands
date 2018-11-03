package org.academiadecodigo.bootcamp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static GameHandler gameHandler;
    private ServerSocket serverSocket;
    private ExecutorService cachedPool;
    private Socket clientSocket;
    private List<Game> listOfGames;
    private static Set<String> clients;


    public Server() {
        cachedPool = Executors.newCachedThreadPool();
        listOfGames = new LinkedList<>();
        gameHandler = new GameHandler();
        clients = new HashSet<>();
    }

    public void init() {
        try {
            System.out.print("PORT: ");
            serverSocket = new ServerSocket(scanner());
        } catch (IOException e) {
            e.printStackTrace();
        }
        readClientSet();
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

                clientName = "Guest" + ++counter;
                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientName, clientSocket);
                newThread(clientHandler);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void joinGame(ClientHandler client) {
        gameHandler.clientJoin(client);
    }

    public static void saveLog(String buffer) {

        synchronized (Game.class) {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream("resources/scoreLog.txt");
                outputStream.write(buffer.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveClientSet() {

        synchronized (this) {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream("resources/clientSet.txt");

                for (String name : clients) {

                    String toWrite = name + "\n";
                    outputStream.write(toWrite.getBytes());
                    outputStream.flush();
                    
                }

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readClientSet() {

        synchronized (this) {
            try {
                FileReader reader = new FileReader("resources/clientSet.txt");

                BufferedReader bufferedReader = new BufferedReader(reader);

                String name = "";

                while ((name = bufferedReader.readLine()) != null) {
                    clients.add(name);
                }

                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void addClientToSet(String name) {
        clients.add(name);
    }

    public static synchronized Set<String> getClients() {
        return clients;
    }

    private int scanner() {
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
