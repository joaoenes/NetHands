package org.academiadecodigo.bootcamp.enums;

public enum ClientOption {
    PLAY(1, "play"),
    SCORE(2, "score"),
    QUIT(3, "quit");

    private String name;
    private Integer option;

    ClientOption(Integer option, String name) {
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

        for (ClientOption option : values()) {
            messages[option.option - 1] = option.name;
        }
        return messages;
    }

    public static String getNameByNumber(Integer option) {
        String chosenOption = null;

        for (ClientOption hand : values()) {
            if (hand.getOption().equals(option)) {
                chosenOption = hand.getName();
            }
        }
        return chosenOption;
    }
}
