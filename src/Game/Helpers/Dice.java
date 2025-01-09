package Game.Helpers;

import java.util.Random;

public class Dice {
    private static Dice instance; // Singleton instance
    private final Random random;

    private Dice() {
        random = new Random();
    }

    public static Dice getInstance() {
        if (instance == null) {
            instance = new Dice();
        }
        return instance;
    }

    public int roll() {
        return random.nextInt(6) + 1;
    }
}
