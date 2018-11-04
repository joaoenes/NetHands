package org.academiadecodigo.bootcamp.client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.client.promptview.*;
import org.academiadecodigo.bootcamp.enums.*;
import org.academiadecodigo.bootcamp.messages.Messages;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static org.academiadecodigo.bootcamp.enums.GameState.*;

public class Client {
    private Prompt prompt;
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private GameState gameState;
    private boolean guest;

    public Client() {
        prompt = new Prompt(System.in, System.out);
        gameState = MAIN;
        guest = true;
    }

    public static void main(String[] args) {

        Client client = new Client();
        client.init();
        client.run();
    }

    private void init() {
        StringQuestion serverQuestion = new StringQuestion(prompt, Messages.SERVER);
        String serverAddress = serverQuestion.ask();

        IntegerQuestion portQuestion = new IntegerQuestion(prompt, Messages.PORT);
        Integer serverPort = portQuestion.ask();

        try {
            clientSocket = new Socket(InetAddress.getByName(serverAddress), serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(Messages.WELCOME);

            while (!clientSocket.isClosed()) {

                switch (gameState) {
                    case MAIN:
                        inMain();
                        break;

                    case LOBBY:
                        inLobby();
                        break;

                    case LOGIN:
                        inLogin();
                        break;

                    case GAME:
                        inGame();
                        break;

                    case REGISTER:
                        inRegister();
                        break;

                    case SCORE:
                        inScore();
                        break;

                    case QUIT:
                        clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStreams();
        }
    }

    private void reactionToServer(Integer input) {
        ServerResponse response = ServerResponse.values()[input];

        switch (response) {
            case PLAY:
                gameState = GAME;
                break;

            case SCORE:
                gameState = SCORE;
                break;

            case LOGIN:
            case INVALID_USER:
            case USER_EXISTS:
                gameState = LOGIN;
                break;

            case LOBBY:
                gameState = LOBBY;
                break;

            case REGISTER:
                gameState = REGISTER;
                break;

            case QUIT:
                gameState = QUIT;
                break;
        }
    }

    private void inLobby() throws IOException {
        String[] options = {Messages.PLAY, Messages.SCORE, Messages.QUIT};
        Menu lobbyMenu = new Menu(prompt, options);
        Integer option = lobbyMenu.show();
        output.println(option);

        if (option - 1 == LobbyOption.QUIT.ordinal()) {
            gameState = QUIT;
            return;
        }

        String message = input.readLine();
        System.out.println(message);

        Integer inputOption = Integer.parseInt(input.readLine());

        reactionToServer(inputOption);
    }

    private void inMain() throws IOException {
        String[] options = {Messages.GUEST, Messages.LOGIN, Messages.REGISTER, Messages.QUIT};
        Menu mainMenu = new Menu(prompt, options);
        Integer option = mainMenu.show();

        output.println(option);

        if (option - 1 == MainMenuOption.QUIT.ordinal()) {
            gameState = QUIT;
            return;
        }

        if (option - 1 == MainMenuOption.GUEST.ordinal()) {
            String message = input.readLine();
            System.out.println(message);
        }

        Integer inputOption = Integer.parseInt(input.readLine());

        reactionToServer(inputOption);
    }

    private void inLogin() throws IOException {
        StringQuestion usernameQuestion = new StringQuestion(prompt, Messages.ASK_USERNAME);
        String username = usernameQuestion.ask();

        output.println(username);

        String message = input.readLine();
        System.out.println(message);

        if (message.equals(Messages.INVALID_USERNAME)) {
            gameState = LOGIN;
            return;
        }

        guest = false;
        gameState = LOBBY;
    }

    private void inGame() throws IOException {
        String[] hands = {Messages.ROCK, Messages.PAPER, Messages.SCISSORS};
        Menu gameMenu = new Menu(prompt, hands);
        Integer option;
        String inputOption;

        inputOption = input.readLine();
        System.out.println(inputOption);

        inputOption = input.readLine();

        while (!inputOption.equals(Messages.GAME_OVER)) {

            System.out.println(Messages.NEW_LINE + inputOption);

            option = gameMenu.show();

            System.out.println(Messages.WAITING_FOR_PLAY);
            output.println(option);

            inputOption = input.readLine();
            System.out.println(Messages.NEW_LINE + inputOption);

            inputOption = input.readLine();
        }

        System.out.println(Messages.NEW_LINE + inputOption);

        inputOption = input.readLine();
        System.out.println(Messages.NEW_LINE + inputOption);

        if (guest) {
            gameState = MAIN;
        } else {
            gameState = LOBBY;
        }
    }

    private void inRegister() throws IOException {
        StringQuestion usernameQuestion = new StringQuestion(prompt, Messages.ASK_USERNAME);
        System.out.println("Username can't contain spaces or " + Messages.ESCAPE_TAG);
        String username = usernameQuestion.ask();

        output.println(username);

        String message = input.readLine();
        System.out.println(message);

        if (message.equals(Messages.INVALID_USERNAME) || message.equals(Messages.REGISTER_NAME_EXISTS) ) {
            gameState = REGISTER;
            return;
        }

        guest = false;
        gameState = LOBBY;
    }

    private void inScore(){
        try {
            System.out.print("You score is: ");
            String messages = input.readLine();
            System.out.print(messages);
            gameState = LOBBY;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeStreams() {
        try {
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
