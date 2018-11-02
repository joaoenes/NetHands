package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import static org.academiadecodigo.bootcamp.Client.Messages.PICK_HAND;
import static org.academiadecodigo.bootcamp.Client.Messages.PICK_OPTION;

public class PromptView {

    public static String showGameMenu(Prompt prompt) {
        String[] hands = { Messages.ROCK, Messages.PAPER, Messages.SCISSORS};

        MenuInputScanner menu = new MenuInputScanner(hands);
        menu.setMessage(PICK_HAND);

        return ClientHand.getNameByNumber(prompt.getUserInput(menu));
    }

    public static String showLobbyMenu(Prompt prompt) {
        String[] options = { Messages.PLAY, Messages.SCORE, Messages.QUIT };

        MenuInputScanner menu = new MenuInputScanner(options);
        menu.setMessage(PICK_OPTION);

        return ClientOption.getNameByNumber(prompt.getUserInput(menu));
    }
}
