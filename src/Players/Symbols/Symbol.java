package Players.Symbols;

public class Symbol implements ISymbol{

    private final String symbol;

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
        this.id = id;
    }
    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void move(int posX, int posY) {
        this.posX += posX;
        this.posY += posY;
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
}
