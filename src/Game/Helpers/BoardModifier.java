package Game.Helpers;

import Players.Helpers.CoordinateObject;
import Players.Symbols.ISymbol;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class used solely to modify the board.
 * Should not be instantiated nor inherited.
 */
public final class BoardModifier {
    private BoardModifier() {}

    /**
     * All the black tiles associated with starting positions and bases.
     */
    private static final ArrayList<CoordinateObject> startingBlacks = new ArrayList<>() {{
        add(new CoordinateObject(2, 2));
        add(new CoordinateObject(2, 3));
        add(new CoordinateObject(3, 2));
        add(new CoordinateObject(3, 3));
        add(new CoordinateObject(2, 11));
        add(new CoordinateObject(2, 12));
        add(new CoordinateObject(3, 11));
        add(new CoordinateObject(3, 12));
        add(new CoordinateObject(11, 11));
        add(new CoordinateObject(11, 12));
        add(new CoordinateObject(12, 11));
        add(new CoordinateObject(12, 12));
        add(new CoordinateObject(11, 2));
        add(new CoordinateObject(11, 3));
        add(new CoordinateObject(12, 2));
        add(new CoordinateObject(12, 3));
        add(new CoordinateObject(1, 8));
        add(new CoordinateObject(8, 13));
        add(new CoordinateObject(6, 1));
        add(new CoordinateObject(13, 6));
    }};

    /**
     * Modifies the board based on the current circumstances.
     * @param board the board to be modified
     * @param outSymbols all symbols currently out of their bases
     * @param inSymbols all symbols currently in their bases
     * @param pastCoordinates the coordinates that were
     * just occupied by the last symbol that moved
     */
    public static void modifyBoard(String[][] board,
                                   ArrayList<ISymbol> outSymbols,
                                   ArrayList<ISymbol> inSymbols,
                                   CoordinateObject pastCoordinates) {

        modifyPastPosition(pastCoordinates, board);

        modifyOutSymbolPositions(outSymbols, board);

        modifyInSymbolPositions(inSymbols, board);
    }

    /**
     * Solely used to modify the symbol of the position
     * last occupied by the last moving symbol.
     * Simply put, brings back the original tile symbol.
     * @param pastCoordinates the coordinates of that position
     * @param board the board to modify the symbol on
     */
    private static void modifyPastPosition(CoordinateObject pastCoordinates, String[][] board) {

        if (pastCoordinates != null) {
            if (pastCoordinates.getX() == 7 && (pastCoordinates.getY() > 0 && pastCoordinates.getY() < 14)) {
                board[pastCoordinates.getX()][pastCoordinates.getY()] = "⬛";
            } else if (pastCoordinates.getY() == 7 && (pastCoordinates.getX() > 0 && pastCoordinates.getX() < 14)) {
                board[pastCoordinates.getX()][pastCoordinates.getY()] = "⬛";
            } else if (startingBlacks.stream().anyMatch((c) -> c.equals(pastCoordinates))) {
                board[pastCoordinates.getX()][pastCoordinates.getY()] = "⬛";
            } else {
                board[pastCoordinates.getX()][pastCoordinates.getY()] = "⬜";
            }
        }
    }

    /**
     * Modifies the symbols on the board in respect to all the symbols out of base.
     * Simply put, renders the players on the board.
     * @param outSymbols all symbols out of their bases
     * @param board the board to modify symbols on
     */
    private static void modifyOutSymbolPositions(ArrayList<ISymbol> outSymbols, String[][] board) {

        if (outSymbols != null) {
            for (ISymbol symbolObj : outSymbols) {
                CoordinateObject symbolCoordinates = symbolObj.getCoordinates();
                board[symbolCoordinates.getX()][symbolCoordinates.getY()] = symbolObj.getSymbol();
            }
        }
    }

    /**
     * Modifies all the symbols in bases. Simply put,
     * changes all the in-base symbols to the player's symbol.
     * Needed in case of player kicks.
     * @param inSymbols all symbols that are in base
     * @param board the board to modify symbols on
     */
    private static void modifyInSymbolPositions(ArrayList<ISymbol> inSymbols, String[][] board) {

        if (inSymbols != null) {
            for (ISymbol symbolObj : inSymbols) {
                CoordinateObject symbolCoordinates = symbolObj.getCoordinates();
                if (!Objects.equals(board[symbolCoordinates.getX()][symbolCoordinates.getY()], symbolObj.getSymbol())) {
                    board[symbolCoordinates.getX()][symbolCoordinates.getY()] = symbolObj.getSymbol();
                }
            }
        }
    }
}
