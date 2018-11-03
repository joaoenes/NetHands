package org.academiadecodigo.bootcamp.client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.enums.GameState;
import org.academiadecodigo.bootcamp.enums.ServerResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static org.academiadecodigo.bootcamp.enums.GameState.*;
import static org.academiadecodigo.bootcamp.messages.Messages.WELCOME;

public class Client {

    private Prompt prompt;
    private String serverAddress;
    private Integer serverPort;
    private Socket clientSocket;
    private GameState gameState;

    public Client() {
        prompt = new Prompt(System.in, System.out);
        gameState = LOGIN;
    }

    public static void main(String[] args) {

        Client client = new Client();
        client.init();
        client.run();
    }

    private void init() {
        serverAddress = PromptView.askServerAddress(prompt);
        serverPort = PromptView.askServerPort(prompt);

        try {
            clientSocket = new Socket(InetAddress.getByName(serverAddress), serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        BufferedReader input;
        PrintWriter output;

        Integer option;
        Integer inputOption;

        System.out.println(WELCOME);

        try {
            while (clientSocket.isConnected()) {

                switch (gameState) {
                    case LOBBY:
                        option = PromptView.showLobbyMenu(prompt);


                        output = new PrintWriter(clientSocket.getOutputStream(), true);
                        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        output.println(option);

                        inputOption = Integer.parseInt(input.readLine());

                        if (inputOption == null) {
                            System.out.println("Connection closed from server side");
                            System.exit(0);
                        }

                        responseToServer(inputOption);
                        break;

                    case GAME:

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer responseToServer(Integer input) {
        ServerResponse response = ServerResponse.values()[input];

        switch (response) {
            case PLAY:
                gameState = GAME;
                return PromptView.showGameMenu(prompt);

            case SCORE:
                gameState = SCORE;
                break;

            case QUIT:
                break;
        }

        return -1;
    }
}

