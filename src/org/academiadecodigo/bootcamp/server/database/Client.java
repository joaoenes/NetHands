package org.academiadecodigo.bootcamp.server.database;

import org.academiadecodigo.bootcamp.messages.Messages;

import java.io.*;

public class Client {

    public static final File FILE = new File("resources/clientList.txt");

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
            String[] temp;
            while ((name = bufferedReader.readLine()) != null) {
                temp = name.split(Messages.ESCAPE_TAG_REGEX);
                if (temp[0].equals(client)) {
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
    public static synchronized boolean checkPass(String name,String password){
        BufferedReader bufferedReader = null;

        try {
            FileReader reader = new FileReader(FILE);

            bufferedReader = new BufferedReader(reader);

            String temp = "";
            String[] tempData;

            while ((temp = bufferedReader.readLine()) != null) {
                tempData = temp.split(Messages.ESCAPE_TAG_REGEX);
                if (tempData[0].equals(name)) {
                    if(tempData[1].equals(password)){
                        return true;
                    }
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
