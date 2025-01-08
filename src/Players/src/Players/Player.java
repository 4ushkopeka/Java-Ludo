package Players.src.Players;

import Players.src.Players.Helpers.CoordinateObject;
import Players.src.Players.Helpers.MoveCalculator;
import Players.src.Players.Symbols.ISymbol;
import Players.src.Players.Symbols.Symbol;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Player implements IPlayer {

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
    public void move(int id, int moveNumber) {
        ISymbol symbol = getSymbolById(id);

        if(symbol == null || !symbol.isOut()) {
            return;
        }

        CoordinateObject newCoordinates = MoveCalculator
                .calculate(symbol.getCoordinates()
                , moveNumber, symbol.canTurnToComplete());

        symbol.move(newCoordinates, new CoordinateObject(startX, startY));
    }

    @Override
    public ISymbol getSymbolById(int id) {
        return symbols.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks to see if a symbol has acquired a point.
     * If it has, adds the point to the player and removes the symbol from the game.
     * @param id the id of the symbol
     * @return <code>true</code> if the symbol has acquired a point,
     * <code>false</code> otherwise
     */
    @Override
    public boolean checkFinish(int id) {
        ISymbol currentSymbol = getSymbolById(id);
        CoordinateObject symbolCoordinates = currentSymbol.getCoordinates();

        if(getSymbol().equals("🔷")) {
            if(symbolCoordinates.getX() == 7 && (symbolCoordinates.getY() >= 6 && symbolCoordinates.getY() < 14)) {
                addPoints(currentSymbol);
                return true;
            }
        } else if (getSymbol().equals("\uD83C\uDF4F")) {
            if(symbolCoordinates.getX() == 7 && (symbolCoordinates.getY() <= 8 && symbolCoordinates.getY() > 0)) {
                addPoints(currentSymbol);
                return true;
            }
        } else if (getSymbol().equals("⭐")) {
            if(symbolCoordinates.getY() == 7 && (symbolCoordinates.getX() >= 6 && symbolCoordinates.getX() < 14)) {
                addPoints(currentSymbol);
                return true;
            }
        }  else if (getSymbol().equals("\uD83C\uDF4E")) {
            if(symbolCoordinates.getY() == 7 && (symbolCoordinates.getX() <= 8 && symbolCoordinates.getX() > 0)) {
                addPoints(currentSymbol);
                return true;
            }
        }
        return false;
    }

    private void addPoints(ISymbol symbol) {
        points++;
        symbols.remove(symbol);
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public ArrayList<ISymbol> getSymbolsOutOfBase() {
        return (ArrayList<ISymbol>) symbols
                .stream()
                .filter(ISymbol::isOut)
                .collect(Collectors.toList());
    }

    @Override
    public ArrayList<ISymbol> getSymbolsInBase() {
        return (ArrayList<ISymbol>) symbols
                .stream()
                .filter(symbol -> !symbol.isOut())
                .collect(Collectors.toList());
    }

    /**
     * @return the string symbol of the current player.
     */
    @Override
    public String getSymbol() {
        return symbols.get(0).getSymbol();
    }

    /**
     * Initialises a symbol to the starter coordinates(unique per player)
     * @return the base coordinates the symbol just occupied
     */
    @Override
    public CoordinateObject initiate() {
        return symbols.stream()
                .filter(symbol -> !symbol.isOut())
                .findFirst()
                .map(symbol -> {
                    symbol.initiate(startX, startY);
                    return symbol.getDefaultCoordinates();
                })
                .orElse(null);
    }

    @Override
    public ISymbol getSymbolByCoordinates(CoordinateObject coordinates) {
        return this.getSymbolsOutOfBase()
                .stream()
                .filter(symbol -> symbol.getCoordinates().equals(coordinates))
        .findFirst()
        .orElse(null);
    }

    /**
     * Supplies the player with their 4 symbols.
     * @param symbol the string value associated
     * with the player and their symbols
     */
    private void setSymbols(String symbol) {
        switch (symbol) {
            case "🔷" -> symbols = new ArrayList<>() {{
                add(new Symbol(symbol, 2, 2, 1));
                add(new Symbol(symbol, 2, 3, 2));
                add(new Symbol(symbol, 3, 2, 3));
                add(new Symbol(symbol, 3, 3, 4));
            }};
            case "⭐" -> symbols = new ArrayList<>() {{
                add(new Symbol(symbol, 2, 11, 1));
                add(new Symbol(symbol, 2, 12, 3));
                add(new Symbol(symbol, 3, 11, 2));
                add(new Symbol(symbol, 3, 12, 4));
            }};
            case "\uD83C\uDF4E" -> symbols = new ArrayList<>() {{
                add(new Symbol(symbol, 11, 2, 1));
                add(new Symbol(symbol, 11, 3, 2));
                add(new Symbol(symbol, 12, 2, 3));
                add(new Symbol(symbol, 12, 3, 4));
            }};
            default -> symbols = new ArrayList<>() {{
                add(new Symbol(symbol, 11, 11, 1));
                add(new Symbol(symbol, 11, 12, 3));
                add(new Symbol(symbol, 12, 11, 2));
                add(new Symbol(symbol, 12, 12, 4));
            }};
        }
    }
}
