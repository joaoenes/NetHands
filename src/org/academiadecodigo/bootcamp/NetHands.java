package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.client.Client;
import org.academiadecodigo.bootcamp.server.Server;

public class NetHands {

    public static void main(String[] args) {

        if (args[0].equals("server")) {
            Server server = new Server();
            server.init();
            server.run();
        } else {
            Client client = new Client();
            client.init();
            client.run();
        }
    }
}
