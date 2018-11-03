package org.academiadecodigo.bootcamp.server;

import java.io.*;

public class CheckClient {

    public static final File FILE = new File("resources/clientSet.txt");

    public static synchronized void saveClient(String client) {

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(FILE));

            writer.write(client, 0, client.length());

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static synchronized boolean clientExists(String client) {
        BufferedReader bufferedReader = null;

        try {
            FileReader reader = new FileReader(FILE);

            bufferedReader = new BufferedReader(reader);

            String name = "";

            while ((name = bufferedReader.readLine()) != null) {
                if (name.equals(client)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }
}
