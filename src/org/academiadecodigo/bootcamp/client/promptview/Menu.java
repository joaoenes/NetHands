package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import static org.academiadecodigo.bootcamp.messages.Messages.PICK_OPTION;

public class Menu {

    private Prompt prompt;
    private String[] options;

    public Menu(Prompt prompt, String[] options) {
        this.prompt = prompt;
        this.options = options;
    }

    public Integer show() {
        MenuInputScanner menu = new MenuInputScanner(options);
        menu.setMessage(PICK_OPTION);

        return prompt.getUserInput(menu);
    }
}
