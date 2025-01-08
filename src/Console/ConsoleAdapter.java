package Console;

import Players.IPlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class aims to adapt the spread out console functions into one. It further adds trivial
 * methods for the game.
 */
public class ConsoleAdapter implements IGameConsole {
    private final BufferedReader reader;

    public ConsoleAdapter() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Prints a line of text to the console.
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Reads a line of text to the console.
     * <p>
     * Note: All input received from the console is in string.
     * </p>
     */
    @Override
    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Prints the whole board with the players.
     */
    @Override
    public void printBoard(String[][] board) {
        for (String[] strings : board) {
            for (String string : strings) System.out.print(string);
            System.out.println();
        }
    }

    /**
     * Introduces the game rules and prints the initial state of the board.
     * @param playerCount the amount of players. Necessary to tell who is who
     * @param board the board to print
     */
    public void introduce(int playerCount, String[][] board) {
        this.print("Welcome to the game of Ludo!");
        this.print("Player One is: \uD83D\uDD37");
        this.print("Player Two is: \uD83C\uDF4F");
        if (playerCount >= 3) {
            this.print("Player Three is: ⭐");
        }
        if (playerCount == 4) {
            this.print("Player Four is: \uD83C\uDF4E");
        }

        this.print("");

        this.print("Rules:");
        this.print("If a player reaches ⏹️, they gain a point and get to roll the dice again. The first player");
        this.print("to reach these tiles with all their symbols wins.");
        this.print("If a player lands on a tile that is occupied by another");
        this.print("player, they kick the current player to their base and keep the spot.");
        this.print("The current player also gets to roll the dice again.");
        this.print("On ⬛ a player cannot be kicked. These tiles are safe.");
        this.print("Players get to roll the dice again if they roll 6.");
        this.print("To get out of base, a player must roll a 6.");
        this.print("Have fun!");

        print("");

        this.print("Board:");
        this.printBoard(board);

        print("");
    }

    /**
     * Prints the Scoreboard of the game.
     * @param players the players whose score is more than 0
     */
    @Override
    public void printScoreboard(ArrayList<IPlayer> players) {
        this.print("Scoreboard:");
        for (IPlayer player : players) {
            this.print("Player " + player.getSymbol() + ": " + player.getPoints());
            if (player.getPoints() == 4) {
                print("");
                this.print("Player " + player.getSymbol() + " wins!");
            }
        }
    }
}

