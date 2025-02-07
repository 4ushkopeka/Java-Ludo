package Game;

import Console.ConsoleAdapter;
import Game.Helpers.BoardFacade;
import Game.Helpers.GameObserver;
import java.util.ArrayList;
import java.util.List;

public abstract class GameEngine {
    protected ConsoleAdapter console;
    protected BoardFacade boardFacade;
    protected String[][] board;
    private final List<GameObserver> observers = new ArrayList<>();

    public GameEngine() {
        console = new ConsoleAdapter();
        boardFacade = new BoardFacade();
    }

    public void start() {
        step();
    }

    private void step() {
        this.update();
        this.visualise();

        if (!this.end()) {
            this.step();
        }
    }

    protected abstract void update();
    protected abstract void visualise();
    protected abstract boolean end();

    protected void initializeBoard(int players) {
        if (players < 2 || players > 4) {
            notifyObservers("Invalid number of players! Game is closing.");
            System.exit(0);
        }
        board = boardFacade.initializeBoard(15, players);
    }

    // Add observer
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    // Notify all observers
    protected void notifyObservers(String message) {
        for (GameObserver observer : observers) {
            observer.update(message);
        }
    }
}
