package org.academiadecodigo.bootcamp.Server;

import org.academiadecodigo.bootcamp.enums.Hand;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private List<ClientHandler> clients;
    private int[] clientScores;
    private int currentRound;
    private int maxRounds;

    public Game(ClientHandler clientHandler1, ClientHandler clientHandler2){
        init();
        clients.add(clientHandler1);
        clients.add(clientHandler2);
    }

    private void init(){
        clients = new LinkedList<>();
        clientScores = new int[2];
        currentRound = 1;
        maxRounds = 5;
    }

    private void roundPlay(Hand client1Hand, Hand client2Hand) {

        if (client1Hand != client2Hand) {
            switch (client1Hand){
                case ROCK:
                    if (client2Hand == Hand.PAPER){
                        addPoint(clients.get(1));
                        return;
                    }
                    break;
                case PAPER:
                    if (client2Hand == Hand.SCISSORS){
                        addPoint(clients.get(1));
                        return;
                    }
                    break;
                case SCISSORS:
                    if (client2Hand == Hand.ROCK){
                        addPoint(clients.get(1));
                        return;
                    }
                    break;
            }

            addPoint(clients.get(0));
            return;
        }
        String tieString = "It's a tie!"; //ADD TO A MESSAGES CLASS
        clients.get(0).send(tieString);
        clients.get(1).send(tieString);
    }

    public void start() {
        String versusPart1String = "You are playing against "; //ADD TO A MESSAGES CLASS
        String versusPart2String = ", good luck!"; //ADD TO A MESSAGES CLASS

        clients.get(0).send(versusPart1String + clients.get(1).getName() + versusPart2String);
        clients.get(1).send(versusPart1String + clients.get(0).getName() + versusPart2String);

        while (currentRound <= maxRounds) {
            String roundString = "ROUND " + currentRound + ": < Waiting for each player >"; //ADD TO A MESSAGES CLASS
            clients.get(0).send(roundString);
            clients.get(1).send(roundString);

            Hand client1Hand;
            Hand client2Hand;

            while ((client1Hand = clients.get(0).getHand()) != null && (client2Hand = clients.get(0).getHand()) != null) {
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

        if (clientHandler == clients.get(0)){
            clientScores[0]++;
            clients.get(0).send(winString);
            clients.get(1).send(looseString);
        }else{
            clientScores[1]++;
            clients.get(0).send(looseString);
            clients.get(1).send(winString);
        }
    }

    private void endGame(){
        String tieString = "The game ended in a tie!"; //ADD TO A MESSAGES CLASS
        String winString = "Congratulations, you won the game!"; //ADD TO A MESSAGES CLASS
        String looseString = "You lost the game!"; //ADD TO A MESSAGES CLASS

        if (clientScores[0] == clientScores[1]){
            clients.get(0).send(tieString);
            clients.get(1).send(tieString);
            clients.get(0).setInGame(false);
            clients.get(1).setInGame(false);
        }

        if (clientScores[0] > clientScores[1]){
            clients.get(0).send(winString);
            clients.get(1).send(looseString);
            clients.get(0).setInGame(false);
            clients.get(1).setInGame(false);
        }

        clients.get(0).send(looseString);
        clients.get(1).send(winString);
        clients.get(0).setInGame(false);
        clients.get(1).setInGame(false);
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
