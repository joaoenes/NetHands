package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import static org.academiadecodigo.bootcamp.Client.Messages.PICK_HAND;

public class PromptView {

    public static String showGameMenu(Prompt prompt) {
        String[] hands = { ClientHand.ROCK.getName(), ClientHand.PAPER.getName(), ClientHand.SCISSORS.getName()};

        MenuInputScanner menu = new MenuInputScanner(hands);
        menu.setMessage(PICK_HAND);

        return ClientHand.getNameByNumber(prompt.getUserInput(menu));
    }
}
