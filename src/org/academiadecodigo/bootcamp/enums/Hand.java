package org.academiadecodigo.bootcamp.enums;

public enum Hand {
    ROCK(1, "rock"),
    PAPER(2, "paper"),
    SCISSORS(3, "scissors");

    private String name;
    private Integer option;

    Hand(Integer option, String name) {
        this.option = option;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getOption() {
        return option;
    }

    public static String[] getNames() {
        String[] messages = new String[values().length];

        for (Hand hand : values()) {
            messages[hand.option - 1] = hand.name;
        }
        return messages;
    }

    public static String getNameByNumber(Integer option) {
        String chosenHand = null;

        for (Hand hand : values()) {
            if (hand.getOption().equals(option)) {
                chosenHand = hand.getName();
            }
        }
        return chosenHand;
    }

}
