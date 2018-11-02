package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import static org.academiadecodigo.bootcamp.Client.Messages.*;

public class PromptView {

    Prompt prompt = new Prompt(System.in, System.out);

    public void showGameMenu() {
        String[] hands = { ROCK_PROMPT, PAPER_PROMPT, SCISSORS_PROMPT};

        MenuInputScanner menu = new MenuInputScanner(hands);
        menu.setMessage(PICK_HAND);

    }
}
