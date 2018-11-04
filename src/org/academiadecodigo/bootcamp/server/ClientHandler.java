package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.enums.Hand;
import org.academiadecodigo.bootcamp.enums.LobbyOption;
import org.academiadecodigo.bootcamp.enums.MainMenuOption;
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
    private boolean logged;

    public ClientHandler(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        this.logged = false;
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

    public void clientCommand() {
        try {
            int userInput = Integer.parseInt(input.readLine());
            checkOption(LobbyOption.values()[userInput - 1]);
        } catch (IOException e) {
            e.printStackTrace();
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
            case QUIT:
                close();
                break;
        }
    }

    public void mainMenu() {
        try {
            int userInput = Integer.parseInt(input.readLine());
            MainMenuOption option = MainMenuOption.values()[userInput - 1];

            switch (option) {
                case LOGIN:
                    output.println(ServerResponse.LOGIN.ordinal());
                    waitLogin();
                    break;
                case GUEST:
                    joinGame();
                    break;
                case REGISTER:
                    waitRegister();
                    break;
                case QUIT:
                    close();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
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
                this.logged = true;
                output.println(Messages.SUCCESSFUL_LOGIN + name);
                clientCommand();
                return;
            }

            output.println(Messages.INVALID_USERNAME);
            waitLogin();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkClientExist(String name) {
        return ClientDB.clientExists(name);
    }

    private void waitRegister() {
        String name = null;

        try {
            name = input.readLine();

            if (name.trim().equals("") || name.contains(Messages.ESCAPE_TAG)) {
                output.println(Messages.INVALID_USERNAME);
                return;
            }

            if (checkClientExist(name)) {
                // output.println(Messages.REGISTER_NAME_EXIST);
                return;
            }

            //output.println(Messages.REGISTER_SUCESS);
            ClientDB.saveClient(name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gameOver() {
        output.println(Messages.GAME_OVER);
    }

    private void register(String name) {

    }

    public void goToMenu(){
        if(logged){
            clientCommand();
            return;
        }
        mainMenu();
    }

    private void joinGame() {
        output.println(Messages.WAITING_FOR_PLAYER);
        Server.joinGame(this);
    }

    public void gameStart() {
        output.println(ServerResponse.PLAY.ordinal());
    }


    public Hand getHand() {
        try {

            int inputHand = Integer.parseInt(input.readLine());
            return Hand.values()[inputHand - 1];

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() {
        mainMenu();
    }

    public void send(String str) {
        output.println(str);
    }

    private void close() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
