package Players.src.Players;

import Players.src.Players.Helpers.CoordinateObject;
import Players.src.Players.Symbols.ISymbol;

import java.util.ArrayList;
import java.util.Random;

public interface IPlayer {

    int rollDice(Random dice);

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
