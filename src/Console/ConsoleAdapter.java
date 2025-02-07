package Console;

import Players.IPlayer;
import Game.Helpers.GameObserver;
import java.util.ArrayList;

public class ConsoleAdapter implements IGameConsole, GameObserver {

    private final Console console;

    public ConsoleAdapter() {
        this.console = new Console();
    }

    @Override
    public void printGameMessage(String message) {
        this.console.print(message);
    }

    @Override
    public String readGameCommand() {
        return this.console.read();
    }

    @Override
    public void printBoard(String[][] board) {
        this.console.printMatrix(board);
    }

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

    @Override
    public void update(String message) {
        printGameMessage(message);
    }
}
