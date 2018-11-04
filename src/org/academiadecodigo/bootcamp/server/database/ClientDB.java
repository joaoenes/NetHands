package org.academiadecodigo.bootcamp.server.database;

import java.io.*;

public class ClientDB {

    public static final File FILE = new File("resources/clientSet.txt");

    public static synchronized void saveClient(String client) {

        FileWriter fileWriter = null;

        try {

            fileWriter = new FileWriter(FILE, true);
            fileWriter.write(client + "\n");
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
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
