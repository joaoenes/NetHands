package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static org.academiadecodigo.bootcamp.Client.Messages.WELCOME;

public class Client {

    private Prompt prompt;
    private String serverAddress;
    private Integer serverPort;
    private Socket clientSocket;


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

        String option;
        String game_option;
        String inputString;

        System.out.println(WELCOME);

        try {
            while (clientSocket.isConnected()) {
                option = PromptView.showLobbyMenu(prompt);

                output = new PrintWriter(clientSocket.getOutputStream(), true);


            }

            option = PromptView.showLobbyMenu(prompt);

            if (option.equals("play")) {

                game_option = PromptView.showGameMenu(prompt);
                System.out.println("Game option: " + game_option);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
