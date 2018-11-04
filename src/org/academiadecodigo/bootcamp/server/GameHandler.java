package org.academiadecodigo.bootcamp.server;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class GameHandler {

    private ExecutorService cachedPool;
    private Queue<ClientHandler> listOfClients;

    GameHandler() {
        cachedPool = Executors.newCachedThreadPool();
        listOfClients = new LinkedList<>();
    }

    void clientJoin(ClientHandler client) {
        listOfClients.offer(client);
        
        if(listOfClients.size() >= 2){

            ClientHandler clientHandler1 = listOfClients.poll();
            ClientHandler clientHandler2 = listOfClients.poll();

            clientHandler1.gameStart();
            clientHandler2.gameStart();

            startGame(clientHandler1, clientHandler2);
        }
    }

    private void startGame(ClientHandler clientHandler1, ClientHandler clientHandler2) {
        cachedPool.submit(new Runnable() {
            @Override
            public void run() {
                new Game(clientHandler1, clientHandler2).start();
            }
        });
    }

    public synchronized void removeClient(ClientHandler clientHandler){
        listOfClients.remove(clientHandler);
    }

}
