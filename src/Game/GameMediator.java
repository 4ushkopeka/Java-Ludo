package Game;

import Players.Helpers.CoordinateObject;
import Players.IPlayer;
import Players.Symbols.ISymbol;

/**
 * Interface for the Mediator in the game.
 * Facilitates communication between players, symbols, and the game board.
 */
public interface GameMediator {
    void playerMove(IPlayer player, int symbolId, int moveNumber);

    boolean tryKick(ISymbol symbol);

    boolean isSafeTile(CoordinateObject coordinates);

    void updateBoard();

    boolean checkGameEnd();
}
