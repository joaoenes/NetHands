package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.enums.LobbyOption;
import org.academiadecodigo.bootcamp.enums.Hand;
import org.academiadecodigo.bootcamp.enums.ServerResponse;
import org.academiadecodigo.bootcamp.messages.Messages;

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
                checkOption(LobbyOption.values()[userInput - 1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void checkOption(LobbyOption option) {
        switch (option) {
            case PLAY:
                joinGame();
                break;
            case SCORE:
                seeScore();
                break;
            case LOGIN:
                waitLogin();
                break;
            case REGISTER:
                waitRegister();
                break;
            case QUIT:
                break;
        }
    }

    private void seeScore() {
        output.println(Score.readScore(name));
    }

    private void waitLogin() {
        try {
            String name = input.readLine();

            if (checkClientExist(name)) {
                this.name = name;
            }

            output.println(Messages.INVALID_USERNAME);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkClientExist(String name) {
        if (Server.getClients().contains(name)) {
            return true;
        }
        return false;
    }

    private void waitRegister() {
        String name = null;

        try {
            name = input.readLine();

            if(name.trim().equals("") || name.contains(Messages.ESCAPE_TAG)){
                output.println(Messages.INVALID_USERNAME);
                return;
            }

            if (checkClientExist(name)) {
                // output.println(Messages.REGISTER_NAME_EXIST);
                return;
            }

            //output.println(Messages.REGISTER_SUCESS);
            Server.addClientToSet(name);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void stillPlaying(){
        output.println(ServerResponse.PLAY.ordinal());
    }

    private void register(String name) {

    }

    private void joinGame() {
        inGame = true;
        Server.joinGame(this);
    }

    public void gameStart() {
        output.println(ServerResponse.PLAY.ordinal());
        output.println();
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public Hand getHand() {
        try {

            int inputHand = Integer.parseInt(input.readLine());
            Hand option = Hand.values()[inputHand - 1];

            switch (option) {
                case ROCK:
                    return Hand.ROCK;
                case PAPER:
                    return Hand.PAPER;
                case SCISSORS:
                    return Hand.SCISSORS;
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
