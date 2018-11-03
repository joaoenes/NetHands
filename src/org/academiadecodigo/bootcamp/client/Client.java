package org.academiadecodigo.bootcamp.client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.enums.ServerResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static org.academiadecodigo.bootcamp.client.Messages.WELCOME;

public class Client {

    private Prompt prompt;
    private String serverAddress;
    private Integer serverPort;
    private Socket clientSocket;
    private Boolean inGame;


    public Client() {
        prompt = new Prompt(System.in, System.out);
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
        String game_option;
        Integer inputOption;

        System.out.println(WELCOME);

        try {
            option = PromptView.showLobbyMenu(prompt);

            while (clientSocket.isConnected()) {
                if (!inGame) {
                    output = new PrintWriter(clientSocket.getOutputStream(),
                            true);
                    input = new BufferedReader(new InputStreamReader(
                            clientSocket.getInputStream()));

                    output.println(option);

                    inputOption = Integer.parseInt(input.readLine());

                    if (inputOption == null) {
                        System.out
                                .println("Connection closed from server side");
                        System.exit(0);
                    }

                    option = responseToServer(inputOption);
                }

                if(inGame) {
                    output = new PrintWriter(clientSocket.getOutputStream(),
                            true);
                    input = new BufferedReader(new InputStreamReader(
                            clientSocket.getInputStream()));

                    output.println(option);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Integer responseToServer(Integer input) {
        ServerResponse response = ServerResponse.values()[input];

        switch (response) {
            case PLAY:
                inGame = true;
                return PromptView.showGameMenu(prompt);

            case SCORE:
                break;

            case QUIT:
                break;
        }

        return -1;
    }
}

