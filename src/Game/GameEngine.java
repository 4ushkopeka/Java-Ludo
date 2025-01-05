package Game;

import Console.ConsoleAdapter;
import Console.IConsole;

public abstract class GameEngine {
    protected IConsole console;

    protected String[][] board;

    public GameEngine() {
        console = new ConsoleAdapter();
    }

    public void start() {
        step();
    }

    private void step() {
        this.move();
        if (!this.end()) {
            this.step();
        }
    }

    protected abstract void move();

    protected abstract boolean end();

    protected void initializeBoard(int players) {
        if (players < 2 || players > 4) {
            console.print("Invalid number of players!");
            console.print("Game is closing.");
            System.exit(0);
        }

        board = new String[15][15];

        initializeBases(board, players);
        initializeCommonRows(board);
        initializeCenter(board);
        initializeCommonColumns(board);
        initializeSpecialTiles(board);
    }

    private void initializeBases(String[][] board, int players) {
        generateBaseTiles(board, 0, 0, "🔷");
        if (players >= 3) {
            generateBaseTiles(board, 0, 9, "⭐");
        } else {
            generateBaseTiles(board, 0, 9, "⬛");
        }
        if (players == 4) {
            generateBaseTiles(board, 9, 0, "\uD83C\uDF4E");
        } else {
            generateBaseTiles(board, 9, 0, "⬛");
        }
        generateBaseTiles(board, 9, 9, "\uD83C\uDF4F");
    }

    private void generateBaseTiles(String[][] board, int startRowPos, int startColPos, String playerTile) {
        for (int i = startRowPos; i < startRowPos + 6; i++) {
            for (int j = startColPos; j < startColPos + 6; j++) {
                if (i == startRowPos || i == startRowPos + 5) {
                    board[i][j] = "\uD83D\uDD33";
                } else if (i == startRowPos + 1 || i == startRowPos + 4) {
                    if (j == startColPos || j == startColPos + 5) {
                        board[i][j] = "\uD83D\uDD33";
                    } else {
                        board[i][j] = "⬜";
                    }
                } else {
                    if (j == startColPos || j == startColPos + 5) {
                        board[i][j] = "\uD83D\uDD33";
                    } else if (j == startColPos + 1 || j == startColPos + 4) {
                        board[i][j] = "⬜";
                    } else {
                        board[i][j] = playerTile;
                    }
                }
            }
        }
    }

    private void initializeCenter(String[][] board) {
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                board[i][j] = "⏹️";
            }
        }
    }

    private void initializeCommonRows(String[][] board) {
        for (int i = 6; i < 9; i++) {
            for (int j = 0; j < 15; j++) {
                if (i != 7) {
                    board[i][j] = "⬜";
                } else {
                    board[i][j] = "⬛";
                }
            }
        }
    }

    private void initializeCommonColumns(String[][] board) {
        for (int i = 0; i < 15; i++) {
            for (int j = 6; j < 9; j++) {
                if (i >= 6 && i < 9) {
                    continue;
                }
                if (j != 7) {
                    board[i][j] = "⬜";
                } else {
                    board[i][j] = "⬛";
                }
            }
        }
    }

    private void initializeSpecialTiles(String[][] board) {
        board[6][1] = "⬛";
        board[1][8] = "⬛";
        board[8][13] = "⬛";
        board[13][6] = "⬛";

        board[7][0] = "⬜";
        board[0][7] = "⬜";
        board[14][7] = "⬜";
        board[7][14] = "⬜";
    }
}