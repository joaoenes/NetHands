package org.academiadecodigo.bootcamp.client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.client.promptview.IntegerQuestion;
import org.academiadecodigo.bootcamp.client.promptview.Menu;
import org.academiadecodigo.bootcamp.client.promptview.StringQuestion;
import org.academiadecodigo.bootcamp.enums.LobbyOption;
import org.academiadecodigo.bootcamp.enums.MainMenuOption;
import org.academiadecodigo.bootcamp.enums.ServerResponse;
import org.academiadecodigo.bootcamp.messages.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Prompt         prompt;
    private Socket         clientSocket;
    private PrintWriter    output;
    private BufferedReader input;
    private ServerResponse clientState;
    private boolean        guest;

    public Client() {
        prompt = new Prompt(System.in, System.out);
        clientState = ServerResponse.MAIN;
        guest = true;
    }

    public static void main(String[] args) {

        Client client = new Client();
        client.init();
        client.run();
    }

    public void init() {
        StringQuestion serverQuestion = new StringQuestion(prompt,
                Messages.SERVER);
        String         serverAddress  = serverQuestion.ask();

        IntegerQuestion portQuestion = new IntegerQuestion(prompt,
                Messages.PORT);
        Integer         serverPort   = portQuestion.ask();

        try {
            clientSocket = new Socket(InetAddress.getByName(serverAddress),
                    serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(Messages.WELCOME);

            while (!clientSocket.isClosed()) {

                switch (clientState) {
                    case MAIN:
                        inMain();
                        break;
                    case LOBBY:
                        inLobby();
                        break;
                    case LOGIN:
                        inLogin();
                        break;
                    case PLAY:
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

    private void inLobby() throws IOException {
        String[] options   = {Messages.PLAY, Messages.SCORE, Messages.QUIT};
        Menu     lobbyMenu = new Menu(prompt, options);
        Integer  option    = lobbyMenu.show();
        
        output.println(option);

        if (option - 1 == LobbyOption.QUIT.ordinal()) {
            clientState = ServerResponse.QUIT;
            return;
        }

        String message = input.readLine();
        System.out.println(message);

        int inputOption = Integer.parseInt(input.readLine());
        clientState = ServerResponse.values()[inputOption];
    }

    private void inMain() throws IOException {
        String[] options  = {Messages.GUEST, Messages.LOGIN, Messages.REGISTER,
                Messages.QUIT};
        Menu     mainMenu = new Menu(prompt, options);
        Integer  option   = mainMenu.show();

        output.println(option);

        if (option - 1 == MainMenuOption.QUIT.ordinal()) {
            clientState = ServerResponse.QUIT;
            return;
        }

        int inputOption = Integer.parseInt(input.readLine());
        clientState = ServerResponse.values()[inputOption];
    }

    private void inLogin() throws IOException {
        StringQuestion usernameQuestion = new StringQuestion(prompt,
                Messages.ASK_USERNAME);
        String         username         = usernameQuestion.ask();

        output.println(username);

        String message = input.readLine();
        System.out.println(message);

        if (message.equals(Messages.INVALID_USERNAME)) {
            clientState = ServerResponse.LOGIN;
            return;
        }

        guest = false;
        clientState = ServerResponse.LOBBY;
    }

    private void inGame() throws IOException {
        String[] hands    = {Messages.ROCK, Messages.PAPER, Messages.SCISSORS};
        Menu     gameMenu = new Menu(prompt, hands);
        Integer  option;
        String   inputOption;

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
            clientState = ServerResponse.MAIN;
        } else {
            clientState = ServerResponse.LOBBY;
        }
    }

    private void inRegister() throws IOException {
        StringQuestion usernameQuestion = new StringQuestion(prompt,
                Messages.ASK_USERNAME);
        System.out.println(
                "Username can't contain spaces or " + Messages.ESCAPE_TAG);
        String username = usernameQuestion.ask();

        output.println(username);

        String message = input.readLine();
        System.out.println(message);

        if (message.equals(Messages.INVALID_USERNAME) || message
                .equals(Messages.REGISTER_NAME_EXISTS)) {
            clientState = ServerResponse.REGISTER;
            return;
        }

        guest = false;
        clientState = ServerResponse.LOBBY;
    }

    private void inScore() throws IOException {
        String messages = input.readLine();
        System.out.print(messages);
        clientState = ServerResponse.LOBBY;
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
