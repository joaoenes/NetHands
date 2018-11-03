package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static org.academiadecodigo.bootcamp.Client.Messages.WELCOME;

public class Client {

    Prompt prompt;
    String serverAddress;
    Integer serverPort;
    Socket clientSocket;

    public static void main(String[] args) {

        Client client = new Client();
        client.init();
        client.run();
    }

    public Client() {
        prompt = new Prompt(System.in, System.out);
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
        String option;
        String game_option;

        System.out.println(WELCOME);

        option = PromptView.showLobbyMenu(prompt);

        if (option.equals("play")) {

            game_option = PromptView.showGameMenu(prompt);
            System.out.println("Game option: " + game_option);
        }

        
    }
}
