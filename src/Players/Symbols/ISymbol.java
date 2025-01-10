package Players.Symbols;

import Game.Visitors.Visitor;
import Players.Helpers.CoordinateObject;

public interface ISymbol {
    void acceptVisitor(Visitor visitor);

    String getSymbol();

    CoordinateObject getCoordinates();

    CoordinateObject getDefaultCoordinates();

    void move(CoordinateObject coordinates, CoordinateObject startCoordinates);

    void kick();

    void initiate(int x, int y);

    boolean isOut();

    int getId();

    boolean canTurnToComplete();
}
