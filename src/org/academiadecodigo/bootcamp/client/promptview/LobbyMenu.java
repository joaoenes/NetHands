package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.messages.Messages;

public class LobbyMenu extends Menu{

    String[] options = { Messages.PLAY, Messages.SCORE, Messages.QUIT };

    public LobbyMenu(Prompt prompt) {
        super(prompt);
        setOptions(options);
    }

}
