package Game;

import Players.Helpers.CoordinateObject;
import Players.IPlayer;
import Players.Player;
import Players.Symbols.ISymbol;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Game extends GameEngine {

    private final Random dice;

    private final ArrayList<CoordinateObject> safeTileCoordinates = new ArrayList<>() {{
        add(new CoordinateObject(6, 1));
        add(new CoordinateObject(1, 8));
        add(new CoordinateObject(8, 13));
        add(new CoordinateObject(13, 6));
    }};

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
    protected void update() {
        IPlayer currentPlayer = players.get(0);
        int moveNumber = currentPlayer.rollDice(dice);
        console.print("Player " + currentPlayer.getSymbol() + " rolled " + moveNumber + "!");
        boolean shouldRotate = true;
        Integer id = null;

        ArrayList<ISymbol> outSymbols = currentPlayer.getSymbolsOutOfBase();

        if (moveNumber < 6) {
            id = this.rolledLessThan6Choices(outSymbols, currentPlayer, moveNumber);
        } else {
            shouldRotate = false;
            id = this.rolled6Choices(outSymbols, currentPlayer, moveNumber);
        }

        if (id != null) {
            if (tryKick(currentPlayer.getSymbolById(id))) {
                shouldRotate = false;

            }

            currentPlayer.checkFinish(id);
        }

        if (shouldRotate) {
            players.add(players.remove(0));
        }
    }

    /**
     * Checks all the current player positions for enemy symbols.
     * Terminates after a symbol has been kicked, since only one
     * conflict is possible per turn due to the nature of the game.
     *
     * @param currentSymbol the symbol that was just moved
     * @return <code>true</code> if a symbol has been successfully kicked,
     * <code>false</code> otherwise
     */
    private boolean tryKick(ISymbol currentSymbol) {

        ArrayList<IPlayer> EnemyPlayers = new ArrayList<IPlayer>(players.subList(1, players.size()));

        for (IPlayer player : EnemyPlayers) {
            ISymbol enemySymbol = player.getSymbolByCoordinates(currentSymbol.getCoordinates());
            if (enemySymbol != null && !onSafeTile(enemySymbol.getCoordinates())) {
                enemySymbol.kick();
                return true;
            }
        }
        return false;
    }

    private boolean onSafeTile(CoordinateObject symbolCoordinates) {
        return safeTileCoordinates.stream().allMatch(symbolCoordinates::equals);
    }

    @Override
    protected void visualise() {

        console.printBoard(board);
        console.print("\n");
    }

    private Integer rolled6Choices(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer, int moveNumber) {
        if (outSymbols.size() == 4) {
            console.print("Choose which symbol to move(Press 1 or 2 or 3 or 4).");
            int id = Integer.parseInt(console.read());
            currentPlayer.move(id, moveNumber);
            return id;
        } else if (!outSymbols.isEmpty()) {
            console.print("You have symbol(s) out. Press 1 if you want to move on, or 2 to bring out another one.");
            if (console.read().equals("1")) {
                console.print("Choose which symbol to move(Press " + outSymbols
                        .stream()
                        .map(ISymbol::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")) + ", respectful to a symbol's id).");
                int id = Integer.parseInt(console.read());
                currentPlayer.move(id, moveNumber);
                return id;
            } else {
                currentPlayer.initiate();
                return null;
            }
        } else {
            currentPlayer.initiate();
            return null;
        }
    }

    private Integer rolledLessThan6Choices(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer, int moveNumber) {
        if (!outSymbols.isEmpty()) {
            console.print("You have " + outSymbols.size() + " symbol(s) out.");
            console.print("Their ids are respectively: " + outSymbols
                    .stream()
                    .map(ISymbol::getId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")) + ".");
            console.print("Choose the symbol to move:");
            int id = Integer.parseInt(console.read());
            currentPlayer.move(id, moveNumber);
            return id;
        } else {
            console.print("You have no moves! Moving on.");
            return null;
        }
    }

    @Override
    protected boolean end() {
        return players
                .stream()
                .map(IPlayer::getPoints)
                .anyMatch(points -> points == 4);
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
