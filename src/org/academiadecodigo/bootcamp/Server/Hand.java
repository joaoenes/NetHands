package org.academiadecodigo.bootcamp.Server;

public enum Hand {
    ROCK("rock"),
    PAPER("paper"),
    SCISSORS("scissors");

    private String name;

    Hand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
