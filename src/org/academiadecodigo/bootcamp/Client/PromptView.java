package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import static org.academiadecodigo.bootcamp.Client.Messages.*;

public class PromptView {

    public static Integer showGameMenu(Prompt prompt) {
        String[] hands = { ROCK_PROMPT, PAPER_PROMPT, SCISSORS_PROMPT};

        MenuInputScanner menu = new MenuInputScanner(hands);
        menu.setMessage(PICK_HAND);

        return prompt.getUserInput(menu);
    }
}
