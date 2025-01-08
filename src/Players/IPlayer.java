package Players;

import Players.Helpers.CoordinateObject;
import Players.Symbols.ISymbol;

import java.util.ArrayList;
import java.util.Random;

public interface IPlayer {

    int rollDice(Random dice);

    void move(int id, int moveNumber);

    void checkFinish(int id);

    ISymbol getSymbolById(int id);

    int getPoints();

    ArrayList<ISymbol> getSymbolsOutOfBase();

    String getSymbol();

    void initiate();

    ISymbol getSymbolByCoordinates(CoordinateObject coordinates);
}
