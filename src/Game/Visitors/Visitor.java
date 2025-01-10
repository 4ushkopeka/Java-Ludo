package Game.Visitors;

import Players.IPlayer;
import Players.Symbols.ISymbol;

public interface Visitor {
    void visitPlayer(IPlayer player);
    void visitSymbol(ISymbol symbol);
}
