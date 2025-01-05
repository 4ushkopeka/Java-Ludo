package Players;

import Players.Symbols.ISymbol;
import Players.Symbols.Symbol;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Player implements IPlayer{

    private int points;

    private final int startX;

    private final int startY;

    private ArrayList<ISymbol> symbols;

    public Player(int startX, int startY, String symbol) {
        this.startX = startX;
        this.startY = startY;
        points = 0;
        setSymbols(symbol);
    }

    @Override
    public int rollDice(Random dice) {
        return dice.nextInt(6) + 1;
    }

    @Override
    public void move(int id) {

    }

    @Override
    public void addPoint() {
        points++;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public ArrayList<ISymbol> getSymbolsOutOfBase() {
        return (ArrayList<ISymbol>) symbols.stream().filter(ISymbol::isOut).collect(Collectors.toList());
    }

    @Override
    public String getSymbol() {
        return symbols.get(0).getSymbol();
    }

    @Override
    public void initiate() {
        symbols.stream()
                .filter(symbol -> !symbol.isOut())
                .findFirst()
                .ifPresent(symbol -> symbol.initiate(startX, startY));
    }

    private void setSymbols(String symbol) {
        switch (symbol) {
            case "🔷" -> symbols = new ArrayList<ISymbol>() {{
                add(new Symbol(symbol, 2, 2, 1));
                add(new Symbol(symbol, 2, 3, 2));
                add(new Symbol(symbol, 3, 2, 3));
                add(new Symbol(symbol, 3, 3, 4));
            }};
            case "\uD83C\uDF4E" -> symbols = new ArrayList<ISymbol>() {{
                add(new Symbol(symbol, 2, 11, 1));
                add(new Symbol(symbol, 2, 12, 3));
                add(new Symbol(symbol, 3, 11, 2));
                add(new Symbol(symbol, 3, 12, 4));
            }};
            case "⭐" -> symbols = new ArrayList<ISymbol>() {{
                add(new Symbol(symbol, 11, 2, 1));
                add(new Symbol(symbol, 11, 3, 2));
                add(new Symbol(symbol, 12, 2, 3));
                add(new Symbol(symbol, 12, 3, 4));
            }};
            default -> symbols = new ArrayList<ISymbol>() {{
                add(new Symbol(symbol, 11, 11, 1));
                add(new Symbol(symbol, 11, 12, 3));
                add(new Symbol(symbol, 12, 11, 2));
                add(new Symbol(symbol, 12, 12, 4));
            }};
        }
    }
}
