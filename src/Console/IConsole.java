package Console;

public interface IConsole {
    void print(String message);

    String read();

    void printBoard(String[][] board);

    void introduce(int totalPlayers, String[][] board);
}
