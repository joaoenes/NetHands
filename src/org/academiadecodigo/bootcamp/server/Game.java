package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.enums.Hand;
import org.academiadecodigo.bootcamp.enums.ServerResponse;
import org.academiadecodigo.bootcamp.messages.Messages;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private List<ClientHandler> listOfClients;
    private int[] clientScores;
    private int currentRound;
    private int maxRounds;

    public Game(ClientHandler clientHandler1, ClientHandler clientHandler2){
        init();
        listOfClients.add(clientHandler1);
        listOfClients.add(clientHandler2);
    }

    private void init(){
        listOfClients = new LinkedList<>();
        clientScores = new int[2];
        currentRound = 1;
        maxRounds = 5;
    }

    private void roundPlay(Hand client1Hand, Hand client2Hand) {

        if (client1Hand != client2Hand) {
            switch (client1Hand){
                case ROCK:
                    if (client2Hand == Hand.PAPER){
                        addPoint(listOfClients.get(1));
                        return;
                    }
                    break;
                case PAPER:
                    if (client2Hand == Hand.SCISSORS){
                        addPoint(listOfClients.get(1));
                        return;
                    }
                    break;
                case SCISSORS:
                    if (client2Hand == Hand.ROCK){
                        addPoint(listOfClients.get(1));
                        return;
                    }
                    break;
            }

            addPoint(listOfClients.get(0), client1Hand, client2Hand);
            return;
        }

        listOfClients.get(0).send(listOfClients.get(1).getName() + Messages.OPPONENT_PLAYED + client2Hand + Messages.ROUND_TIE);
        listOfClients.get(1).send(listOfClients.get(0).getName() + Messages.OPPONENT_PLAYED + client1Hand + Messages.ROUND_TIE);
    }

    public void start() {
        listOfClients.get(0).send(Messages.VERSUS_PART1 + listOfClients.get(1).getName() + Messages.VERSUS_PART2);
        listOfClients.get(1).send(Messages.VERSUS_PART1 + listOfClients.get(0).getName() + Messages.VERSUS_PART2);

        Hand client1Hand;
        Hand client2Hand;

        while (currentRound <= maxRounds) {
            newRoundMessage();

            clientWaiting();

            while ((client1Hand = listOfClients.get(0).getHand()) != null && (client2Hand = listOfClients.get(1).getHand()) != null) {
                roundPlay(client1Hand, client2Hand);
                break;
            }

            currentRound++;
        }

        gameOver();
        endGame();

    }

    private void newRoundMessage(){
        listOfClients.get(0).send(Messages.ROUND_PART1 + currentRound + Messages.ROUND_PART2);
        listOfClients.get(1).send(Messages.ROUND_PART1 + currentRound + Messages.ROUND_PART2);
    }

    private void clientWaiting(){
        listOfClients.get(0).send(Messages.WAITING_FOR_PLAY);
        listOfClients.get(1).send(Messages.WAITING_FOR_PLAY);
    }

    private void gameOver(){
        listOfClients.get(0).gameOver();
        listOfClients.get(1).gameOver();
    }

    private void addPoint(ClientHandler clientHandler, Hand client1Hand, Hand client2Hand){
        if (clientHandler == listOfClients.get(0)) {
            clientScores[0]++;
            listOfClients.get(0).send(listOfClients.get(1).getName() + Messages.OPPONENT_PLAYED + client2Hand + Messages.ROUND_WIN);
            listOfClients.get(1).send(listOfClients.get(0).getName() + Messages.OPPONENT_PLAYED + client1Hand + Messages.ROUND_LOST);
        }else{
            clientScores[1]++;
            listOfClients.get(0).send(listOfClients.get(1).getName() + Messages.OPPONENT_PLAYED + client2Hand + Messages.ROUND_LOST);
            listOfClients.get(1).send(listOfClients.get(0).getName() + Messages.OPPONENT_PLAYED + client1Hand + Messages.ROUND_WIN);
        }
    }

    private void endGame(){

        String client1status = Messages.WON_LOG;
        String client2stauts = Messages.WON_LOG;

        if (clientScores[0] == clientScores[1]){
            listOfClients.get(0).send(Messages.GAME_TIE);
            listOfClients.get(1).send(Messages.GAME_TIE);
            client1status = Messages.TIE_LOG;
            client2stauts = Messages.TIE_LOG;
        }

        if (clientScores[0] > clientScores[1]){
            listOfClients.get(0).send(Messages.GAME_WON);
            listOfClients.get(1).send(Messages.GAME_LOST);
            client2stauts = Messages.LOST_LOG;
        }else{
            listOfClients.get(0).send(Messages.GAME_LOST);
            listOfClients.get(1).send(Messages.GAME_WON);
            client1status = Messages.LOST_LOG;
        }

        listOfClients.get(0).setInLobby(false);
        listOfClients.get(1).setInLobby(false);
        Score.saveLog(listOfClients.get(0) + Messages.ESCAPE_TAG + client1status + Messages.ESCAPE_TAG + listOfClients.get(1) + Messages.NEW_LINE);
        Score.saveLog(listOfClients.get(1) + Messages.ESCAPE_TAG + client2stauts + Messages.ESCAPE_TAG + listOfClients.get(2) + Messages.NEW_LINE);
    }
}
