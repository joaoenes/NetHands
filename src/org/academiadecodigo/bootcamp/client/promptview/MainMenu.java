package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.messages.Messages;

public class MainMenu extends Menu{

    public MainMenu(Prompt prompt) {
        super(prompt);
        String[] options = {Messages.GUEST, Messages.LOGIN, Messages.REGISTER, Messages.QUIT};
        setOptions(options);
    }
}
