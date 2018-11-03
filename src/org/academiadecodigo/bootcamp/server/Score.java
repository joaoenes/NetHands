package org.academiadecodigo.bootcamp.server;

import java.io.*;

public class Score {

    private static final String PATH_SCORELOG = "scoreLog.txt";
    private static final String ROOT_PATH = "resources/";
    private static final File file = new File(ROOT_PATH + PATH_SCORELOG);

    public static void readScore(){

    }

    public static void saveLog(String name, int score) {

        synchronized (Game.class) {
            FileOutputStream output = null;

            try {

                String line = "&"+name+"&"+score;

                output = new FileOutputStream(file);

                output.write(line.getBytes());

                output.flush();

                output.close();


            } catch (IOException e) {

                e.printStackTrace();

            }finally {

                if(output != null){
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
