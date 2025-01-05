package Console;

public interface IConsole {
    public void print(String message);

    public String read();

    public void printBoard(String[][] board);

    public void introduce(int totalPlayers, String[][] board);
}
