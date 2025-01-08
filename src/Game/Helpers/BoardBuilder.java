package Game.Helpers;

public class BoardBuilder {
    private String[][] board;

    /**
     * Initializes the board with the specified size.
     * @param size - The size of the board (e.g., 15x15).
     * @return BoardBuilder - The current instance of the builder.
     */
    public BoardBuilder initialize(int size) {
        board = new String[size][size];
        return this;
    }

    /**
     * Initializes the base tiles for the specified number of players.
     * @param players - The number of players (2, 3, or 4).
     * @returns BoardBuilder - The current instance of the builder.
     */
    public BoardBuilder initializeBases(int players) {
        generateBaseTiles(0, 0, "🔷");
        generateBaseTiles(0, 9, players >= 3 ? "⭐" : "⬛");
        generateBaseTiles(9, 0, players == 4 ? "\uD83C\uDF4E" : "⬛");
        generateBaseTiles(9, 9, "\uD83C\uDF4F");
        return this;
    }

    /**
     * Initializes the center tiles of the board.
     * @returns BoardBuilder - The current instance of the builder.
     */
    public BoardBuilder initializeCenter() {
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                board[i][j] = "⏹️";
            }
        }
        return this;
    }

    /**
     * Initializes the common rows on the board.
     * @returns BoardBuilder - The current instance of the builder.
     */
    public BoardBuilder initializeCommonRows() {
        for (int i = 6; i < 9; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = i != 7 ? "⬜" : "⬛";
            }
        }
        return this;
    }

    /**
     * Initializes the common columns on the board.
     * @returns BoardBuilder - The current instance of the builder.
     */
    public BoardBuilder initializeCommonColumns() {
        for (int i = 0; i < 15; i++) {
            for (int j = 6; j < 9; j++) {
                if (i >= 6 && i < 9) continue;
                board[i][j] = j != 7 ? "⬜" : "⬛";
            }
        }
        return this;
    }

    /**
     * Places special tiles (safe and protected tiles) on the board.
     * @returns BoardBuilder - The current instance of the builder.
     */
    public BoardBuilder initializeSpecialTiles() {
        board[6][1] = "⬛";
        board[1][8] = "⬛";
        board[8][13] = "⬛";
        board[13][6] = "⬛";

        board[7][0] = "⬜";
        board[0][7] = "⬜";
        board[14][7] = "⬜";
        board[7][14] = "⬜";

        return this;
    }

    /**
     * Generates tiles for a specific base section of the board.
     * @private
     * @param startRowPos - The starting row position for the base.
     * @param startColPos - The starting column position for the base.
     * @param playerTile - The tile symbol for the player's base.
     */
    private void generateBaseTiles(int startRowPos, int startColPos, String playerTile) {
        for (int i = startRowPos; i < startRowPos + 6; i++) {
            for (int j = startColPos; j < startColPos + 6; j++) {
                if (i == startRowPos || i == startRowPos + 5 || j == startColPos || j == startColPos + 5) {
                    board[i][j] = "\uD83D\uDD33";
                } else if ((i == startRowPos + 1 || i == startRowPos + 4) && (j == startColPos + 1 || j == startColPos + 4)) {
                    board[i][j] = "⬜";
                } else {
                    board[i][j] = playerTile;
                }
            }
        }
    }

    /**
     * Finalizes and returns the constructed board.
     * @returns {string[][]} The constructed game board.
     */
    public String[][] build() {
        return board;
    }
}
