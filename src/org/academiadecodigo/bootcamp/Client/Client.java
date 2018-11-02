package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;

public class Client {

    public static void main(String[] args) {

        Prompt prompt = new Prompt(System.in, System.out);

        PromptView.showGameMenu(prompt);

    }
}
