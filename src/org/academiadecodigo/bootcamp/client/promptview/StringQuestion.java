package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

public class StringQuestion {

    private Prompt prompt;
    private String message;

    public StringQuestion(Prompt prompt, String message) {
        this.prompt = prompt;
        this.message = message;
    }

    public String askPass(){
        PasswordInputScanner scanner = new PasswordInputScanner();
        scanner.setMessage(message);
        return prompt.getUserInput(scanner);
    }

    public String ask() {
        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage(message);

        return prompt.getUserInput(scanner);
    }
}
