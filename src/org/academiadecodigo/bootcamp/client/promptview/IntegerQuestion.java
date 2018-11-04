package org.academiadecodigo.bootcamp.client.promptview;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;

public class IntegerQuestion {

    private Prompt prompt;
    private String message;

    public IntegerQuestion(Prompt prompt, String message) {
        this.prompt = prompt;
        this.message = message;
    }

    public Integer ask() {
        IntegerInputScanner scanner = new IntegerInputScanner();
        scanner.setMessage(message);

        return prompt.getUserInput(scanner);
    }
}
