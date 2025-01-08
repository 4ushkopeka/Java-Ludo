package Players.src.Players.Symbols;

import Players.src.Players.Helpers.CoordinateObject;

public interface ISymbol {

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
