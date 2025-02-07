package Console;

import Players.IPlayer;

import java.util.ArrayList;

/**
 * This class aims to adapt the primitive console interface to the necessities of the game.
 */
public class ConsoleAdapter implements IGameConsole {

    private final Console console;

    public ConsoleAdapter() {
        this.console = new Console();
    }

    /**
     * Prints an in-game message.
     * @param message the message to print
     */
    @Override
    public void printGameMessage(String message) {
        this.console.print(message);
    }

    /**
     * Reads a command from the console interface.
     * @return a string representing the action to be undertaken by the game
     */
    @Override
    public String readGameCommand() {
       return this.console.read();
    }

    /**
     * Prints the game bord.
     * @param board a 2x2 string matrix
     */
    @Override
    public void printBoard(String[][] board) {
        this.console.printMatrix(board);
    }

    /**
     * Introduces the game rules and prints the initial state of the board.
     * @param totalPlayers the amount of players. Necessary to tell who is who
     * @param board the board to print
     */
    @Override
    public void introduce(int totalPlayers, String[][] board) {
        this.console.print("Welcome to the game of Ludo!");
        this.console.print("Player One is: \uD83D\uDD37");
        this.console.print("Player Two is: \uD83C\uDF4F");
        if (totalPlayers >= 3) {
            this.console.print("Player Three is: ⭐");
        }
        if (totalPlayers == 4) {
            this.console.print("Player Four is: \uD83C\uDF4E");
        }

        this.console.print("");

        this.console.print("Rules:");
        this.console.print("If a player reaches ⏹️, they gain a point and get to roll the dice again. The first player");
        this.console.print("to reach these tiles with all their symbols wins.");
        this.console.print("If a player lands on a tile that is occupied by another");
        this.console.print("player, they kick the current player to their base and keep the spot.");
        this.console.print("The current player also gets to roll the dice again.");
        this.console.print("On ⬛ a player cannot be kicked. These tiles are safe.");
        this.console.print("Players get to roll the dice again if they roll 6.");
        this.console.print("To get out of base, a player must roll a 6.");
        this.console.print("Have fun!");

        this.console.print("");

        this.console.print("Board:");
        this.console.printMatrix(board);

        this.console.print("");
    }

    /**
     * Prints the Scoreboard of the game.
     * @param players the players whose score is more than 0
     */
    @Override
    public void printScoreboard(ArrayList<IPlayer> players) {
        this.console.print("Scoreboard:");
        for (IPlayer player : players) {
            this.console.print("Player " + player.getSymbol() + ": " + player.getPoints());
            if (player.getPoints() == 4) {
                console.print("");
                this.console.print("Player " + player.getSymbol() + " wins!");
            }
        }
    }
}
