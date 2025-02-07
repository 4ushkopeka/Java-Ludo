package Game;

import Console.ConsoleAdapter;
import Game.Helpers.BoardFacade;

public abstract class GameEngine {
    protected ConsoleAdapter console;
    protected BoardFacade boardFacade;
    protected String[][] board;

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
            console.printGameMessage("Invalid number of players!");
            console.printGameMessage("Game is closing.");
            System.exit(0);
        }

        board = boardFacade.initializeBoard(15, players);
    }
}
