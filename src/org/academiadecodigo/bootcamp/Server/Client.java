package org.academiadecodigo.bootcamp.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private String name;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Client(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        init();
    }

    private void init() {
        try {

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public Hand getHand() {
        try {
            String inString = input.readLine();

            for (Hand hand : Hand.values()) {
                if (inString.equals(hand.getName())) {
                    return hand;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run(){

    }

    public void send(String str) {
        output.write(str);
    }

    private void close() {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (!socket.isClosed()) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
