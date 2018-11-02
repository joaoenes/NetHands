package org.academiadecodigo.bootcamp.Server;

public class Game {

    private Client client1;
    private Client client2;
    private int client1points = 0;
    private int client2points = 0;
    private int currentRound = 1;
    private int maxRounds;

    private void roundPlay(Hand client1Hand, Hand client2Hand) {
        currentRound++;

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

        System.out.println("It's a tie!");
    }

    public void start() {
        while (currentRound <= maxRounds) {
            System.out.println("ROUND " + currentRound + ": < Waiting for each player >");

            Hand client1Hand;
            Hand client2Hand;

            while ((client1Hand = client1.getHand()) != null && (client2Hand = client2.getHand()) != null) {
                roundPlay(client1Hand, client2Hand);
                break;
            }
        }

        getWinner();
    }


    public void setClient1(Client client1) {
        this.client1 = client1;
    }

    public void setClient2(Client client2) {
        this.client2 = client2;
    }

    public void setRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    private void addPoint(Client client){
        if (client == client1){
            client1points++;
        }else{
            client2points++;
        }

        System.out.println("Player "+client.getName()+" wins!");
    }

    private Client getWinner(){
        if (client1points == client2points){
            return null;
        }

        if (client1points > client2points){
            return client1;
        }

        return client2;
    }

}
