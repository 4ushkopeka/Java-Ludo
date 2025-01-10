package Players.Symbols;

import Game.Visitors.Visitor;
import Players.Helpers.CoordinateObject;

public class Symbol implements ISymbol{

    private final String symbol;

    private boolean canTurnToComplete;

    private final int id;

    private int posX;

    private int posY;

    /**
     * Base coordinates for the symbol.
     */
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
    public void acceptVisitor(Visitor visitor) {
        visitor.visitSymbol(this);
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public CoordinateObject getCoordinates() {
        return new CoordinateObject(posX, posY);
    }

    /**
     * @return the base coordinates of the symbol
     */
    @Override
    public CoordinateObject getDefaultCoordinates() {
        return new CoordinateObject(defaultX, defaultY);
    }

    @Override
    public void move(CoordinateObject coordinates, CoordinateObject startCoordinates) {
        this.posX = coordinates.getX();
        this.posY = coordinates.getY();

        if(!canTurnToComplete) {
            turnToCompleteLogic(startCoordinates);
        }
    }

    /**
     * Checks if the symbol has made a complete lap,
     * allowing it to go for a point if it has.
     * @param startCoordinates the coordinates that a
     * symbol from that base is initialised to
     */
    private void turnToCompleteLogic(CoordinateObject startCoordinates) {
        if(symbol.equals("🔷") && startCoordinates.getX() == posX - 2 && posY < 6) {
            shouldTurnToComplete();
        } else if (symbol.equals("\uD83C\uDF4F") && startCoordinates.getX() == posX + 2 && posY > 8) {
            shouldTurnToComplete();
        } else if (symbol.equals("⭐") && startCoordinates.getY() == posY + 2 && posX < 6) {
            shouldTurnToComplete();
        }  else if (symbol.equals("\uD83C\uDF4E") && startCoordinates.getY() == posY - 2 && posX > 8) {
            shouldTurnToComplete();
        }
    }

    private void shouldTurnToComplete() {
        canTurnToComplete = true;
    }

    /**
     * Brings the symbol back to their base.
     */
    @Override
    public void kick() {
        posX = defaultX;
        posY = defaultY;
    }

    /**
     *Gets a symbol out of their base.
     * @param x the x-coordinate to move the symbol to
     * @param y the y-coordinate to move the symbol to
     */
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
