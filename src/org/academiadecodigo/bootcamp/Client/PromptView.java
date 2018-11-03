package org.academiadecodigo.bootcamp.Client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.enums.ClientOption;
import org.academiadecodigo.bootcamp.enums.Hand;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import static org.academiadecodigo.bootcamp.Client.Messages.*;

public class PromptView {

    public static String showGameMenu(Prompt prompt) {
        String[] hands = { Messages.ROCK, Messages.PAPER, Messages.SCISSORS};

        MenuInputScanner menu = new MenuInputScanner(hands);
        menu.setMessage(PICK_HAND);

        return Hand.getNameByNumber(prompt.getUserInput(menu));
    }

    public static String showLobbyMenu(Prompt prompt) {
        String[] options = { Messages.PLAY, Messages.SCORE, Messages.QUIT };

        MenuInputScanner menu = new MenuInputScanner(options);
        menu.setMessage(PICK_OPTION);

        return ClientOption.getNameByNumber(prompt.getUserInput(menu));
    }

    public static String askServerAddress(Prompt prompt) {
        StringInputScanner server = new StringInputScanner();
        server.setMessage(SERVER);

        return prompt.getUserInput(server);
    }

    public static Integer askServerPort(Prompt prompt) {
        IntegerInputScanner port = new IntegerInputScanner();
        port.setMessage(PORT);

        return prompt.getUserInput(port);
    }


}
