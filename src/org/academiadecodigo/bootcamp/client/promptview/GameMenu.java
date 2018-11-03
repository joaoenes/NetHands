package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.messages.Messages;

public class GameMenu extends Menu{

    String[] hands = { Messages.ROCK, Messages.PAPER, Messages.SCISSORS};

    public GameMenu(Prompt prompt) {
        super(prompt);
        setOptions(hands);
    }
}
