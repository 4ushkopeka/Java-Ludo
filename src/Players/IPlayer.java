package Players;

import Players.Helpers.CoordinateObject;
import Players.Symbols.ISymbol;
import Game.Visitors.Visitor;

import java.util.ArrayList;
import java.util.Random;

public interface IPlayer {
    void acceptVisitor(Visitor visitor);

    void move(int id, int moveNumber);

    boolean checkFinish(int id);

    ISymbol getSymbolById(int id);

    int getPoints();

    ArrayList<ISymbol> getSymbolsOutOfBase();

    ArrayList<ISymbol> getSymbolsInBase();

    String getSymbol();

    CoordinateObject initiate();

    ISymbol getSymbolByCoordinates(CoordinateObject coordinates);
}
