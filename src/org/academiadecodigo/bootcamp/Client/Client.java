package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;

import static org.academiadecodigo.bootcamp.Client.Messages.WELCOME;

public class Client {

    public static void main(String[] args) {


        Prompt prompt = new Prompt(System.in, System.out);
        String option;
        String game_option;

        System.out.println(WELCOME);

        option = PromptView.showLobbyMenu(prompt);
        System.out.println("I want to " + option);

        if (option.equals("play")) {

            game_option = PromptView.showGameMenu(prompt);
            System.out.println("Game option: " + game_option);
        }
    }
}
