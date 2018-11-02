package org.academiadecodigo.bootcamp.Server;

public enum Hand {
    ROCK("Rock"),
    PAPER("Paper"),
    SCISSORS("Scissors");

    private String name;

    Hand(String name) {
        this.name = name;
    }
}
