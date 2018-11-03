package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.messages.Messages;

import java.io.*;

public class Score {

    private static final String PATH_SCORELOG = "scoreLog.txt";
    private static final String ROOT_PATH = "resources/";
    private static final File file = new File(ROOT_PATH + PATH_SCORELOG);

    public static String readScore(String clientName) {
        synchronized (Game.class) {
            BufferedReader reader = null;
            int score = 0;

            try {

                reader = new BufferedReader(new FileReader(file));

                String line = "";
                String[] lineWords;

                while ((line = reader.readLine()) != null) {

                    lineWords = line.split(Messages.ESCAPE_TAG);

                    if (lineWords[0].equals(clientName)) {
                        switch (lineWords[1]) {
                            case Messages.TIE_LOG:
                                score += 1;
                                break;
                            case Messages.WON_LOG:
                                score += 2;
                                break;
                            default:
                                break;
                        }
                    }
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
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

    }

    public static void saveLog(String line) {

        synchronized (Game.class) {
            FileOutputStream output = null;

            try {

                output = new FileOutputStream(file);

                output.write(line.getBytes());

                output.flush();

                output.close();

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
