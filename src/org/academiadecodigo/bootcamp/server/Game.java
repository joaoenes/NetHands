package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.enums.Hand;
import org.academiadecodigo.bootcamp.messages.Messages;
import org.academiadecodigo.bootcamp.server.database.Score;

import java.util.LinkedList;
import java.util.List;

class Game {

    private List<ClientHandler> listOfClients;
    private int[] clientScores;
    private int currentRound;
    private int maxRounds;

    Game(ClientHandler clientHandler1, ClientHandler clientHandler2){
        init();
        listOfClients.add(clientHandler1);
        listOfClients.add(clientHandler2);
    }

    private void init(){
        listOfClients = new LinkedList<>();
        clientScores = new int[2];
        currentRound = 1;
        maxRounds = 1;
    }


    private void roundPlay(Hand client1Hand, Hand client2Hand) {

        if (client1Hand != client2Hand) {
            switch (client1Hand){
                case ROCK:
                    if (client2Hand == Hand.PAPER){
                        addPoint(listOfClients.get(1), client1Hand, client2Hand);
                        return;
                    }
                    break;
                case PAPER:
                    if (client2Hand == Hand.SCISSORS){
                        addPoint(listOfClients.get(1), client1Hand, client2Hand);
                        return;
                    }
                    break;
                case SCISSORS:
                    if (client2Hand == Hand.ROCK){
                        addPoint(listOfClients.get(1), client1Hand, client2Hand);
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

    void start() {
        listOfClients.get(0).send(Messages.VERSUS_PART1 + listOfClients.get(1).getName() + Messages.VERSUS_PART2);
        listOfClients.get(1).send(Messages.VERSUS_PART1 + listOfClients.get(0).getName() + Messages.VERSUS_PART2);

        while (currentRound <= maxRounds) {
            newRoundMessage();
            roundHands();
            currentRound++;
        }

        gameOver();
        endGame();
    }

    private void roundHands(){

        Hand[] clientHands = new Hand[2];

        if ((clientHands[0] = listOfClients.get(0).getHand()) != null && (clientHands[1] = listOfClients.get(1).getHand()) != null) {

            roundPlay(clientHands[0], clientHands[1]);

        }

    }

    private void newRoundMessage(){
        listOfClients.get(0).send(Messages.ROUND_PART1 + currentRound + Messages.ROUND_PART2);
        listOfClients.get(1).send(Messages.ROUND_PART1 + currentRound + Messages.ROUND_PART2);
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

        listOfClients.get(0).goToMenu();
        listOfClients.get(1).goToMenu();

        Score.saveLog(listOfClients.get(0).getName() + Messages.ESCAPE_TAG + client1status + Messages.ESCAPE_TAG +
                listOfClients.get(1).getName() + Messages.NEW_LINE);
        Score.saveLog(listOfClients.get(1).getName() + Messages.ESCAPE_TAG + client2stauts + Messages.ESCAPE_TAG +
                listOfClients.get(2).getName() + Messages.NEW_LINE);
    }
}
