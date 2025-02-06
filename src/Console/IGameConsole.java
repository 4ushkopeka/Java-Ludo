package Console;

import Players.IPlayer;

import java.util.ArrayList;

public interface IGameConsole {
    void printGameMessage(String message);

    String readGameCommand();

    void printBoard(String[][] board);

    void introduce(int totalPlayers, String[][] board);

    void printScoreboard(ArrayList<IPlayer> players);
}
