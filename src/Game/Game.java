package Game;

import Players.IPlayer;
import Players.Player;
import Players.Symbols.ISymbol;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Game extends GameEngine {

    private final Random dice;

    private final ArrayList<IPlayer> players = new ArrayList<>();

    public Game() {
        super();

        console.print("Choose amount of players(2-4): ");
        int totalPlayers = Integer.parseInt(console.read());

        super.initializeBoard(totalPlayers);

        dice = new Random();

        console.introduce(totalPlayers, board);
        this.initializePlayers(totalPlayers);
    }

    @Override
    protected void move() {
       IPlayer currentPlayer = players.get(0);
       int moveNumber = currentPlayer.rollDice(dice);
       console.print("Player " + currentPlayer.getSymbol() + " rolled " + moveNumber + "!");
       boolean rotate = true;

       ArrayList<ISymbol> outSymbols = currentPlayer.getSymbolsOutOfBase();
       if (moveNumber < 6) {
           this.rolledLessThan6Choices(outSymbols, currentPlayer);
       } else {
           rotate = false;
           this.rolled6Choices(outSymbols, currentPlayer);
       }
       if (rotate) {
           players.add(players.remove(0));
       }
       console.printBoard(board);
       console.print("\n");
    }

    private void rolled6Choices(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer) {
        if (outSymbols.size() == 4) {
            console.print("Choose which symbol to move(Press 1 or 2 or 3 or 4).");
            currentPlayer.move(Integer.parseInt(console.read()));
        } else if (!outSymbols.isEmpty()) {
            console.print("You have symbol(s) out. Press 1 if you want to move on, or 2 to bring out another one.");
            if (console.read().equals("1")) {
                console.print("Choose which symbol to move(Press " + outSymbols.stream().map(ISymbol::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")) + ".");
                currentPlayer.move(Integer.parseInt(console.read()));
            } else {
                currentPlayer.initiate();
            }
        } else {
            currentPlayer.initiate();
        }
    }

    private void rolledLessThan6Choices(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer) {
        if (!outSymbols.isEmpty()) {
            console.print("You have " + outSymbols.size() + " symbol(s) out.");
            console.print("Their ids are respectively: " + outSymbols.stream().map(ISymbol::getId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")) + ".");
            console.print("Choose the symbol to move:");
            currentPlayer.move(Integer.parseInt(console.read()));
        } else {
            console.print("You have no moves! Moving on.");
        }
    }

    @Override
    protected boolean end() {

        return false;
    }

    private void initializePlayers(int totalPlayers) {
        players.add(new Player(6, 1, "\uD83D\uDD37"));
        players.add(new Player(8, 13, "\uD83C\uDF4F"));
        if (totalPlayers >= 3) {
            players.add(new Player(1, 8, "⭐"));
        }
        if (totalPlayers == 4) {
            players.add(new Player(13, 6, "\uD83C\uDF4E"));
        }
    }
}
