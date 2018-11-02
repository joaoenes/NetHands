package org.academiadecodigo.bootcamp.Server;

public class Game {

    private Client client1;
    private Client client2;
    private int client1points = 0;
    private int client2points = 0;
    private int currentRound = 1;
    private int maxRounds = 5;

    public Game(Client client1, Client client2){
        this.client1 = client1;
        this.client2 = client2;
    }

    private void roundPlay(Hand client1Hand, Hand client2Hand) {

        if (client1Hand != client2Hand) {
            switch (client1Hand){
                case ROCK:
                    if (client2Hand == Hand.PAPER){
                        addPoint(client2);
                        return;
                    }
                    break;
                case PAPER:
                    if (client2Hand == Hand.SCISSORS){
                        addPoint(client2);
                        return;
                    }
                    break;
                case SCISSORS:
                    if (client2Hand == Hand.ROCK){
                        addPoint(client2);
                        return;
                    }
                    break;
            }

            addPoint(client1);
            return;
        }
        String tieString = "It's a tie!"; //ADD TO A MESSAGES CLASS
        client1.send(tieString);
        client2.send(tieString);
    }

    public void start() {
        while (currentRound <= maxRounds) {
            String roundString = "ROUND " + currentRound + ": < Waiting for each player >"; //ADD TO A MESSAGES CLASS
            client1.send(roundString);
            client2.send(roundString);

            Hand client1Hand;
            Hand client2Hand;

            while ((client1Hand = client1.getHand()) != null && (client2Hand = client2.getHand()) != null) {
                roundPlay(client1Hand, client2Hand);
                break;
            }

            currentRound++;
        }

        endGame();

    }

    private void addPoint(Client client){
        String winString = "You win this round!"; //ADD TO A MESSAGES CLASS
        String looseString = "You lost this round!"; //ADD TO A MESSAGES CLASS

        if (client == client1){
            client1points++;
            client1.send(winString);
            client2.send(looseString);
        }else{
            client2points++;
            client1.send(looseString);
            client2.send(winString);
        }
    }

    private void endGame(){
        String tieString = "The game ended in a tie!"; //ADD TO A MESSAGES CLASS
        String winString = "Congratulations, you won the game!"; //ADD TO A MESSAGES CLASS
        String looseString = "You lost the game!"; //ADD TO A MESSAGES CLASS

        if (client1points == client2points){
            client1.send(tieString);
            client2.send(tieString);
        }

        if (client1points > client2points){
            client1.send(winString);
            client2.send(looseString);
        }

        client1.send(looseString);
        client2.send(winString);
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
