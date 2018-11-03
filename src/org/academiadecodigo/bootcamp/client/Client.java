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

import static org.academiadecodigo.bootcamp.client.Messages.WELCOME;
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

        System.out.println(WELCOME);

        try {
            while (clientSocket.isConnected()) {

                switch (gameState) {


                    case LOBBY:
                        inLobby();
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
                break;

            case SCORE:
                gameState = SCORE;
                break;

            case QUIT:
                break;
        }

        return -1;
    }

    private void inLobby() throws IOException {
        Integer option = PromptView.showLobbyMenu(prompt);

        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        output.println(option);

        Integer inputOption = Integer.parseInt(input.readLine());

        if (inputOption == null) {
            System.out.println("Connection closed from server side");
            System.exit(0);
        }

        responseToServer(inputOption);
    }
}

