package org.academiadecodigo.bootcamp.server.database;

import org.academiadecodigo.bootcamp.messages.Messages;

import java.io.*;

public class Score {
    private static final File file = new File("resources/scoreLog.txt");

    public static synchronized String readScore(String clientName) {
        BufferedReader reader = null;
        int score = 0;
        try {
            reader = new BufferedReader(new FileReader(file));

            String line = "";
            String[] lineWords;

            while ((line = reader.readLine()) != null) {

                lineWords = line.split(Messages.ESCAPE_TAG_REGEX);

                if (lineWords[0].equals(clientName)) {

                    if (lineWords[1].equals(Messages.TIE_LOG)) {
                        score = score + 1;
                        break;
                    }
                    if (lineWords[1].equals(Messages.WON_LOG)) {
                        score = score + 2;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return clientName + " " + score;
    }

    public static synchronized void saveLog(String log) {

        FileWriter fileWriter = null;

        try {

            fileWriter = new FileWriter(file, true);
            fileWriter.write(log + "\n");
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
}

