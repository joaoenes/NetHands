package org.academiadecodigo.bootcamp.Server;

import org.academiadecodigo.bootcamp.enums.ClientOption;
import org.academiadecodigo.bootcamp.enums.Hand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {

    private String name;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean inGame = false;

    public ClientHandler(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        init();
    }

    private void init() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void clientCommand() {

        while (!inGame) {
            try {
                int userInput = Integer.parseInt(input.readLine());
                checkOption(ClientOption.values()[userInput - 1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void checkOption(ClientOption option) {
        switch (option) {
            case PLAY:
                inGame = true;
                Server.joinGame(this);
                break;
            case SCORE:
                break;
            case QUIT:
                break;
        }
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public Hand getHand() {
        try {
            String inString = input.readLine();
            for (Hand hand : Hand.values()) {
                if (hand.getName().equals(inString)) {
                    return hand;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() {
        clientCommand();
    }

    public void send(String str) {
        output.println(str);
    }

    private void close() {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (!socket.isClosed()) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
