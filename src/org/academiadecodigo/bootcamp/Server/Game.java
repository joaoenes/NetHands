package org.academiadecodigo.bootcamp.Server;

public class Game {

    private ClientHandler clientHandler1;
    private ClientHandler clientHandler2;
    private int client1points;
    private int client2points;
    private int maxRounds;

    public Game(ClientHandler clientHandler1, ClientHandler clientHandler2){
        this.clientHandler1 = clientHandler1;
        this.clientHandler2 = clientHandler2;
        this.maxRounds = 5;
        this.client1points = 0;
        this.client2points = 0;
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

        System.out.println("It's a tie!");
    }

    public void start() {
        int currentRound = 1;
        while (currentRound <= maxRounds) {
            System.out.println("ROUND " + currentRound + ": < Waiting for each player >");

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
        if (clientHandler == clientHandler1){
            client1points++;
        }else{
            client2points++;
        }

        System.out.println("Player "+ clientHandler.getName()+" wins this round!");
    }

    private void endGame(){
        String tieString = "The game ended in a tie!";
        String winString = "Congratulations, you won the game!";
        String looseString = "You lost the game!";

        if (client1points == client2points){
            clientHandler1.send(tieString);
            clientHandler1.send(tieString);
        }

        if (client1points > client2points){
            clientHandler1.send(winString);
            clientHandler1.send(looseString);
        }

        clientHandler1.send(looseString);
        clientHandler1.send(winString);
    }

    public void setRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }
}
