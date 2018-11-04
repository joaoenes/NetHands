package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.messages.Messages;

public class LobbyMenu extends Menu{

    public LobbyMenu(Prompt prompt) {
        super(prompt);
        String[] options = {Messages.PLAY, Messages.SCORE, Messages.QUIT};
        setOptions(options);
    }

}
