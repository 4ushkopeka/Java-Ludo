package Game.Helpers;

import Players.Player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return Integer.compare(o2.getPoints(), o1.getPoints());
    }
}
