package Game;

import Game.Helpers.Dice;
import Game.Visitors.ScoreCalculatorVisitor;
import Players.Helpers.CoordinateObject;
import Players.IPlayer;
import Players.Player;
import Players.Symbols.ISymbol;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Game extends GameEngine {
    private final Dice dice;

    private CoordinateObject pastCoordinates;

    /**
     * Tiles that shield players from clashes.
     */
    private final ArrayList<CoordinateObject> safeTileCoordinates = new ArrayList<>() {{
        add(new CoordinateObject(6, 1));
        add(new CoordinateObject(1, 8));
        add(new CoordinateObject(8, 13));
        add(new CoordinateObject(13, 6));
    }};

    private final ArrayList<IPlayer> players = new ArrayList<>();

    public Game() {
        super();

        console.printGameMessage("Choose amount of players(2-4): ");
        int totalPlayers = Integer.parseInt(console.readGameCommand());

        super.initializeBoard(totalPlayers);

        dice = Dice.getInstance(); // Initializes Dice singleton class - design pattern

        super.initializeBoard(totalPlayers);

        console.introduce(totalPlayers, board);
        this.initializePlayers(totalPlayers);

        pastCoordinates = null;
    }

    @Override
    protected void update() {
        IPlayer currentPlayer = players.get(0);
        int moveNumber = dice.roll();
        console.printGameMessage("Player " + currentPlayer.getSymbol() + " rolled " + moveNumber + "!");
        boolean shouldRotate = true;
        Integer id;

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
            if (currentPlayer.checkFinish(id)) {
                console.printGameMessage("Player " + currentPlayer.getSymbol() + " scored!");
                console.printGameMessage("Their current score is:" + currentPlayer.getPoints());
                shouldRotate = false;
            }
        }

        if (shouldRotate) {
            players.add(players.remove(0));
        }
    }

    /**
     * Checks the current player's moved symbol position for enemy symbols.
     * Terminates after a symbol has been kicked, since only one
     * conflict is possible per turn due to the nature of the game.
     * @param currentSymbol the symbol that was just moved
     * @return <code>true</code> if a symbol has been successfully kicked,
     * <code>false</code> otherwise
     */
    private boolean tryKick(ISymbol currentSymbol) {

        ArrayList<IPlayer> EnemyPlayers = new ArrayList<>(players.subList(1, players.size()));

        for (IPlayer player : EnemyPlayers) {
            ISymbol enemySymbol = player.getSymbolByCoordinates(currentSymbol.getCoordinates());
            if (enemySymbol != null && !onSafeTile(enemySymbol.getCoordinates())) {
                enemySymbol.kick();
                console.printGameMessage("Player "
                        + currentSymbol.getSymbol()
                        + " kicked player "
                        + enemySymbol.getSymbol()
                        + "'s symbol with id "
                        + enemySymbol.getId() + "!");
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a symbol is on a safe tile (black square)
     * @param symbolCoordinates the symbol's coordinates
     * @return <code>true</code> if the symbol is safe,
     * <code>false</code> otherwise
     */
    private boolean onSafeTile(CoordinateObject symbolCoordinates) {
        return safeTileCoordinates.stream().anyMatch(symbolCoordinates::equals);
    }

    @Override
    protected void visualise() {
        ScoreCalculatorVisitor scoreCalculator = new ScoreCalculatorVisitor();

        // Let the visitor calculate the score for all players
        for (IPlayer player : players) {
            player.acceptVisitor(scoreCalculator);
        }

        int totalScore = scoreCalculator.getTotalScore();
        console.printGameMessage("Total score of all players: " + totalScore);

        ArrayList<ISymbol> allOutSymbols = players.stream()
                .flatMap(player -> player.getSymbolsOutOfBase().stream())
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<ISymbol> allinSymbols = players.stream()
                .flatMap(player -> player.getSymbolsInBase().stream())
                .collect(Collectors.toCollection(ArrayList::new));

        boardFacade.modifyBoard(allOutSymbols, allinSymbols, pastCoordinates);

        ArrayList<IPlayer> scoredPlayers = players
                .stream()
                .filter(p -> p.getPoints() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
        if (!scoredPlayers.isEmpty()) {
            scoredPlayers.sort(Comparator.comparingInt(IPlayer::getPoints));
            console.printScoreboard(scoredPlayers);
        }

        console.printBoard(boardFacade.getBoard());
        console.printGameMessage("\n");
    }

    /**
     * Presents the player with their options if they roll a 6.
     * @param outSymbols the player's symbols out of base
     * @param currentPlayer the current player
     * @param moveNumber the number they rolled
     * @return the id of the symbol <code>(int)</code> the player chooses to move,
     * or <code>null</code> if they make a mistake.
     */
    private Integer rolled6Choices(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer, int moveNumber) {
        if (outSymbols.size() == 4 - currentPlayer.getPoints()) {
            console.printGameMessage("Choose which symbol to move(Press " + outSymbols
                    .stream()
                    .map(ISymbol::getId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(" or ")) + ").");
            return move(outSymbols, currentPlayer, moveNumber);
        } else if (!outSymbols.isEmpty()) {
            console.printGameMessage("You have symbol(s) out. Press 1 if you want to move on, or 2 to bring out another one.");
            if (console.readGameCommand().equals("1")) {
                console.printGameMessage("Choose which symbol to move(Press " + outSymbols
                        .stream()
                        .map(ISymbol::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")) + ", respectful to a symbol's id).");
                return move(outSymbols, currentPlayer, moveNumber);
            } else {
                pastCoordinates = currentPlayer.initiate();
                return null;
            }
        } else {
            pastCoordinates = currentPlayer.initiate();
            return null;
        }
    }

    /**
     * Moves a symbol, chosen by the player, if it exists
     * and is out of base.
     * @param outSymbols the player's symbols out of the base
     * @param currentPlayer the current player
     * @param moveNumber the number they rolled
     * @return the id of the moved symbol <code>(int)</code>, <code>null</code>
     * if the player did not move anything(made a mistake).
     */
    private Integer move(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer, int moveNumber) {
        int id = Integer.parseInt(console.readGameCommand());
        if (outSymbols.stream().map(ISymbol::getId).toList().contains(id)) {
            pastCoordinates = currentPlayer.getSymbolById(id).getCoordinates();
            currentPlayer.move(id, moveNumber);
            return id;
        }
        console.printGameMessage("Wrong id. You miss this opportunity.");
        return null;
    }

    /**
     * Presents the player with their options if they roll less than a 6.
     * @param outSymbols the player's symbols out of base
     * @param currentPlayer the current player
     * @param moveNumber the number they rolled
     * @return the id of the symbol the player chooses to move
     * <code>(int)</code> or <code>null</code> if they make a mistake
     * or cannot move anything.
     */
    private Integer rolledLessThan6Choices(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer, int moveNumber) {
        if (!outSymbols.isEmpty()) {
            console.printGameMessage("You have " + outSymbols.size() + " symbol(s) out.");
            console.printGameMessage("Their ids are respectively: " + outSymbols
                    .stream()
                    .map(ISymbol::getId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")) + ".");
            console.printGameMessage("Choose the symbol to move:");
            return move(outSymbols, currentPlayer, moveNumber);
        } else {
            console.printGameMessage("You have no moves! Moving on.");
            return null;
        }
    }

    /**
     * Checks to see if the game has been completed.
     * @return <code>true</code> if there is a player with 4 points,
     * <code>false</code> otherwise
     */
    @Override
    protected boolean end() {
        return players
                .stream()
                .map(IPlayer::getPoints)
                .anyMatch(points -> points == 4);
    }

    /**
     * Adds the requested amount of players to the game.
     * @param totalPlayers the amount of players
     */
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
