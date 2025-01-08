package Game;

import Console.ConsoleAdapter;
import Console.IGameConsole;
import Game.Helpers.BoardBuilder;

public abstract class GameEngine {
    protected IGameConsole console;
    protected String[][] board;

    public GameEngine() {
        console = new ConsoleAdapter();
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

        BoardBuilder builder = new BoardBuilder();
        board = builder
                .initialize(15)
                .initializeBases(players)
                .initializeCommonRows()
                .initializeCenter()
                .initializeCommonColumns()
                .initializeSpecialTiles()
                .build();
    }
}
