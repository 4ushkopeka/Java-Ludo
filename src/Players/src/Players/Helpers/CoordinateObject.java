package Players.src.Players.Helpers;

public class CoordinateObject {
    private int x;

    private int y;

    public CoordinateObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(CoordinateObject other) {
        return x == other.getX() && y == other.getY();
    }

    public void incrementX() {
        x++;
    }

    public void incrementY() {
        y++;
    }

    public void decrementX() {
        x--;
    }

    public void decrementY() {
        y--;
    }
}
