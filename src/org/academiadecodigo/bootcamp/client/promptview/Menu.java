package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import static org.academiadecodigo.bootcamp.messages.Messages.PICK_OPTION;

public abstract class Menu {

    private Prompt prompt;
    private String[] options;

    Menu(Prompt prompt) {
        this.prompt = prompt;
    }

    public Integer show() {
        MenuInputScanner menu = new MenuInputScanner(options);
        menu.setMessage(PICK_OPTION);

        return getPrompt().getUserInput(menu);
    }

    private Prompt getPrompt() {
        return prompt;
    }

    void setOptions(String[] options) {
        this.options = options;
    }
}
