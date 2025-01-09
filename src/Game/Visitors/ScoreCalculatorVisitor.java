package Game.Visitors;

import Players.IPlayer;
import Players.Symbols.ISymbol;

public class ScoreCalculatorVisitor implements Visitor {
    private int totalScore;

    @Override
    public void visitPlayer(IPlayer player) {
        totalScore += player.getPoints();
    }

    @Override
    public void visitSymbol(ISymbol symbol) {
        // No specific action for symbols in this visitor
    }

    public int getTotalScore() {
        return totalScore;
    }
}
