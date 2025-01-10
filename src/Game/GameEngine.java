package Game;

import Console.ConsoleAdapter;
import Console.IGameConsole;
import Game.Helpers.BoardFacade;

public abstract class GameEngine {
    protected IGameConsole console;
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
            console.print("Invalid number of players!");
            console.print("Game is closing.");
            System.exit(0);
        }

        board = boardFacade.initializeBoard(15, players);
    }
}
