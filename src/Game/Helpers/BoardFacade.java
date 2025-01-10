package Game.Helpers;

import Players.Symbols.ISymbol;

import java.util.ArrayList;

public class BoardFacade {
    private final BoardBuilder boardBuilder;
    private String[][] board;

    public BoardFacade() {
        boardBuilder = new BoardBuilder();
    }

    /**
     * Initializes the board with all components.
     * @param size the size of the board.
     * @param players the number of players.
     * @return the initialized board.
     */
    public String[][] initializeBoard(int size, int players) {
        board = boardBuilder
                .initialize(size)
                .initializeBases(players)
                .initializeCommonRows()
                .initializeCenter()
                .initializeCommonColumns()
                .initializeSpecialTiles()
                .build();
        return board;
    }

    /**
     * Modifies the board based on the state of the game.
     * @param outSymbols symbols that are out of their bases.
     * @param inSymbols symbols that are still in their bases.
     * @param pastCoordinates the last position of a symbol that moved.
     */
    public void modifyBoard(
            ArrayList<ISymbol> outSymbols,
            ArrayList<ISymbol> inSymbols,
            Players.Helpers.CoordinateObject pastCoordinates) {
        BoardModifier.modifyBoard(board, outSymbols, inSymbols, pastCoordinates);
    }

    /**
     * Gets the current board state.
     * @return the board.
     */
    public String[][] getBoard() {
        return board;
    }
}
