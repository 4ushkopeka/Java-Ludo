package Players.Symbols;

import Players.Helpers.CoordinateObject;

public interface ISymbol {

    String getSymbol();

    CoordinateObject getCoordinates();

    void move(CoordinateObject coordinates, CoordinateObject startCoordinates);

    void kick();

    void initiate(int x, int y);

    boolean isOut();

    int getId();

    boolean canTurnToComplete();
}
