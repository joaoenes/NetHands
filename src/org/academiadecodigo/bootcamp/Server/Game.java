package org.academiadecodigo.bootcamp.Server;

import org.academiadecodigo.bootcamp.enums.Hand;

public class Game {

    private ClientHandler clientHandler1;
    private ClientHandler clientHandler2;
    private int client1points = 0;
    private int client2points = 0;
    private int currentRound = 1;
    private int maxRounds = 5;

    public Game(ClientHandler clientHandler1, ClientHandler clientHandler2){
        this.clientHandler1 = clientHandler1;
        this.clientHandler2 = clientHandler2;
    }

    private void roundPlay(Hand client1Hand, Hand client2Hand) {

        if (client1Hand != client2Hand) {
            switch (client1Hand){
                case ROCK:
                    if (client2Hand == Hand.PAPER){
                        addPoint(clientHandler2);
                        return;
                    }
                    break;
                case PAPER:
                    if (client2Hand == Hand.SCISSORS){
                        addPoint(clientHandler2);
                        return;
                    }
                    break;
                case SCISSORS:
                    if (client2Hand == Hand.ROCK){
                        addPoint(clientHandler2);
                        return;
                    }
                    break;
            }

            addPoint(clientHandler1);
            return;
        }
        String tieString = "It's a tie!"; //ADD TO A MESSAGES CLASS
        clientHandler1.send(tieString);
        clientHandler2.send(tieString);
    }

    public void start() {
        while (currentRound <= maxRounds) {
            String roundString = "ROUND " + currentRound + ": < Waiting for each player >"; //ADD TO A MESSAGES CLASS
            clientHandler1.send(roundString);
            clientHandler2.send(roundString);

            Hand client1Hand;
            Hand client2Hand;

            while ((client1Hand = clientHandler1.getHand()) != null && (client2Hand = clientHandler2.getHand()) != null) {
                roundPlay(client1Hand, client2Hand);
                break;
            }

            currentRound++;
        }

        endGame();

    }

    private void addPoint(ClientHandler clientHandler){
        String winString = "You win this round!"; //ADD TO A MESSAGES CLASS
        String looseString = "You lost this round!"; //ADD TO A MESSAGES CLASS

        if (clientHandler == clientHandler1){
            client1points++;
            clientHandler1.send(winString);
            clientHandler2.send(looseString);
        }else{
            client2points++;
            clientHandler1.send(looseString);
            clientHandler2.send(winString);
        }
    }

    private void endGame(){
        String tieString = "The game ended in a tie!"; //ADD TO A MESSAGES CLASS
        String winString = "Congratulations, you won the game!"; //ADD TO A MESSAGES CLASS
        String looseString = "You lost the game!"; //ADD TO A MESSAGES CLASS

        if (client1points == client2points){
            clientHandler1.send(tieString);
            clientHandler2.send(tieString);
        }

        if (client1points > client2points){
            clientHandler1.send(winString);
            clientHandler2.send(looseString);
        }

        clientHandler1.send(looseString);
        clientHandler2.send(winString);
    }

    /**
     *
     * DESCRIPTION: in case a specific amount of rounds want to be played
     * @param maxRounds
     */
    public void setRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }
}
