package Players.Symbols;

public interface ISymbol {

    public String getSymbol();

    public int getPosX();

    public int getPosY();

    public void move(int dx, int dy);

    public void kick();

    public void initiate(int x, int y);

    public boolean isOut();

    public int getId();
}
