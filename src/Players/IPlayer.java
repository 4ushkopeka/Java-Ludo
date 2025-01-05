package Players;

import Players.Symbols.ISymbol;

import java.util.ArrayList;
import java.util.Random;

public interface IPlayer {

    public int rollDice(Random dice);

    public void move(int id);

    public void addPoint();

    public int getPoints();

    public ArrayList<ISymbol> getSymbolsOutOfBase();

    public String getSymbol();

    public void initiate();
}
