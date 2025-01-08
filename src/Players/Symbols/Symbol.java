package Players.Symbols;

import Players.Helpers.CoordinateObject;

public class Symbol implements ISymbol{

    private final String symbol;

    private boolean canTurnToComplete;

    private final int id;

    private int posX;

    private int posY;

    private final int defaultX;

    private final int defaultY;

    public Symbol(String symbol, int posX, int posY, int id) {
        this.symbol = symbol;
        this.defaultX = posX;
        this.defaultY = posY;
        this.posX = posX;
        this.posY = posY;
        canTurnToComplete = false;
        this.id = id;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public CoordinateObject getCoordinates() {
        return new CoordinateObject(posX, posY);
    }

    @Override
    public void move(CoordinateObject coordinates, CoordinateObject startCoordinates) {
        this.posX += coordinates.getX();
        this.posY += coordinates.getY();

        if(!canTurnToComplete) {
            turnToCompleteLogic(startCoordinates);
        }
    }

    private void turnToCompleteLogic(CoordinateObject startCoordinates) {
        if(symbol.equals("🔷") && startCoordinates.getX() == posX - 2 && posY < 6) {
            shouldTurnToComplete();
        } else if (symbol.equals("\uD83C\uDF4F") && startCoordinates.getX() == posX + 2 && posY > 8) {
            shouldTurnToComplete();
        } else if (symbol.equals("⭐") && startCoordinates.getY() == posY + 2 && posX < 6) {
            shouldTurnToComplete();
        }  else if (symbol.equals("\uD83C\uDF4E") && startCoordinates.getY() == posY + 2 && posX > 8) {
            shouldTurnToComplete();
        }
    }

    private void shouldTurnToComplete() {
        canTurnToComplete = true;
    }

    @Override
    public void kick() {
        posX = defaultX;
        posY = defaultY;
    }

    @Override
    public void initiate(int x, int y) {
        posX = x;
        posY = y;
    }

    @Override
    public boolean isOut() {
        return !(posX == defaultX && posY == defaultY);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean canTurnToComplete() {
        return canTurnToComplete;
    }
}
