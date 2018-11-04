package org.academiadecodigo.bootcamp.client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import static org.academiadecodigo.bootcamp.messages.Messages.*;

class PromptView {

    static String askServerAddress(Prompt prompt) {
        StringInputScanner server = new StringInputScanner();
        server.setMessage(SERVER);

        return prompt.getUserInput(server);
    }

    static Integer askServerPort(Prompt prompt) {
        IntegerInputScanner port = new IntegerInputScanner();
        port.setMessage(PORT);

        return prompt.getUserInput(port);
    }

    static String askUsername(Prompt prompt) {
        StringInputScanner username = new StringInputScanner();
        username.setMessage(ASK_USERNAME);

        return prompt.getUserInput(username);
    }


}
