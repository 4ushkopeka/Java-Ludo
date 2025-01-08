package Players.src.Console;

import Players.src.Players.IPlayer;

import java.util.ArrayList;

public interface IGameConsole {
    void print(String message);

    String read();

    void printBoard(String[][] board);

    void introduce(int totalPlayers, String[][] board);

    void printScoreboard(ArrayList<IPlayer> players);
}
