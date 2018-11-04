package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.messages.Messages;

public class GameMenu extends Menu{

    public GameMenu(Prompt prompt) {
        super(prompt);
        String[] hands = {Messages.ROCK, Messages.PAPER, Messages.SCISSORS};
        setOptions(hands);
    }
}
