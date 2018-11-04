package org.academiadecodigo.bootcamp.messages;

public class Messages {

    /**
     * To ask the clients what is the server address and port they wish to connect
     */
    public static final String SERVER = "What is the server address? ";
    public static final String PORT = "What is the server port? ";

    /**
     * To show to show when connected to server
     */
    public static final String LOGIN = "Login ";
    public static final String WELCOME = "Welcome to Net Hands!";
    public static final String PICK_OPTION = "What do you want to do?";
    public static final String GUEST = "Play as guest";
    public static final String REGISTER = "Register";

    /**
     * After Login
     */
    public static final String ASK_USERNAME = "Username: ";
    public static final String SUCCESSFUL_LOGIN = "Welcome ";
    public static final String INVALID_USERNAME = "Invalid username";

    /**
     * Descriptions of the options of the client prompt
     */
    public static final String QUIT = "Quit";
    public static final String PLAY = "Play new game";
    public static final String SCORE = "See your score";

    /**
     * Descriptions, shown in the prompt of the clients, of the possible options they can choose in-game
     */
    public static final String PICK_HAND = "Pick the hand you want to play ";
    public static final String ROCK = "Rock";
    public static final String PAPER = "Paper";
    public static final String SCISSORS = "Scissors";
    public static final String QUIT_REPEAT = "Play again? ";

    /**
     * To tell the clients the status of the game
     */
    public static final String WAITING_FOR_PLAYER = "< Waiting for another player to join the game >";
    public static final String VERSUS_PART1 = "< You are playing against ";
    public static final String VERSUS_PART2 = ", good luck! >";
    public static final String ROUND_PART1 = "< ROUND ";
    public static final String ROUND_PART2 = " >";
    public static final String WAITING_FOR_PLAY = "< Waiting for opponent to play >";
    public static final String GAME_OVER = "< GAME OVER >";

    /**
     * To tell the clients the result of the round they just played
     */
    public static final String OPPONENT_PLAYED = " played ";
    public static final String ROUND_TIE = ". It's a tie!";
    public static final String ROUND_WIN = ". You won this round!";
    public static final String ROUND_LOST = ". You lost this round!";

    /**
     *  To tell the clients how his game ended
     */
    public static final String GAME_TIE = "The game ended in a tie!";
    public static final String GAME_WON = "Congratulations, you won the game!";
    public static final String GAME_LOST = "You lost the game!";

    /**
     * To write to the score log file
     */
    public static final String ESCAPE_TAG_REGEX = "\\[\\*]";
    public static final String ESCAPE_TAG = "[*]";
    public static final String NEW_LINE = "\r\n";
    public static final String TIE_LOG = "TIE";
    public static final String WON_LOG = "WON";
    public static final String LOST_LOG = "LOST";
}
