package Game;

import Game.Helpers.Dice;
import Game.Helpers.GameObserver;
import Game.Visitors.ScoreCalculatorVisitor;
import Players.Helpers.CoordinateObject;
import Players.IPlayer;
import Players.Player;
import Players.Symbols.ISymbol;
import Console.ConsoleAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Game extends GameEngine {
    private final Dice dice;
    private CoordinateObject pastCoordinates;
    private final ArrayList<IPlayer> players = new ArrayList<>();

    /**
     * Tiles that shield players from clashes.
     */
    private final ArrayList<CoordinateObject> safeTileCoordinates = new ArrayList<>() {{
        add(new CoordinateObject(6, 1));
        add(new CoordinateObject(1, 8));
        add(new CoordinateObject(8, 13));
        add(new CoordinateObject(13, 6));
    }};

    public Game() {
        super();

        ConsoleAdapter consoleAdapter = new ConsoleAdapter();
        this.addObserver(consoleAdapter);

        console.printGameMessage("Choose amount of players(2-4): ");
        int totalPlayers = Integer.parseInt(console.readGameCommand());

        super.initializeBoard(totalPlayers);

        dice = Dice.getInstance();

        super.initializeBoard(totalPlayers);

        console.introduce(totalPlayers, board);
        this.initializePlayers(totalPlayers);

        pastCoordinates = null;
    }

    @Override
    protected void update() {
        IPlayer currentPlayer = players.get(0);
        int moveNumber = dice.roll();

        notifyObservers("Player " + currentPlayer.getSymbol() + " rolled " + moveNumber + "!");

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
                notifyObservers("Player " + currentPlayer.getSymbol() + " scored!");
                notifyObservers("Their current score is: " + currentPlayer.getPoints());
                shouldRotate = false;
            }
        }

        if (shouldRotate) {
            players.add(players.remove(0));
        }
    }

    @Override
    protected boolean end() {
        boolean gameOver = players
                .stream()
                .map(IPlayer::getPoints)
                .anyMatch(points -> points == 4);

        if (gameOver) {
            notifyObservers("Game Over! A player has won.");
        }

        return gameOver;
    }

    @Override
    protected void visualise() {
        ScoreCalculatorVisitor scoreCalculator = new ScoreCalculatorVisitor();

        for (IPlayer player : players) {
            player.acceptVisitor(scoreCalculator);
        }

        int totalScore = scoreCalculator.getTotalScore();
        notifyObservers("Total score of all players: " + totalScore);

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

    private boolean tryKick(ISymbol currentSymbol) {
        ArrayList<IPlayer> enemyPlayers = new ArrayList<>(players.subList(1, players.size()));

        for (IPlayer player : enemyPlayers) {
            ISymbol enemySymbol = player.getSymbolByCoordinates(currentSymbol.getCoordinates());
            if (enemySymbol != null && !onSafeTile(enemySymbol.getCoordinates())) {
                enemySymbol.kick();
                notifyObservers("Player " + currentSymbol.getSymbol() +
                        " kicked player " + enemySymbol.getSymbol() + "'s symbol with id " + enemySymbol.getId() + "!");
                return true;
            }
        }
        return false;
    }

    private boolean onSafeTile(CoordinateObject symbolCoordinates) {
        return safeTileCoordinates.stream().anyMatch(symbolCoordinates::equals);
    }

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
                        .collect(Collectors.joining(", ")) + ").");
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

    private Integer move(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer, int moveNumber) {
        int id = Integer.parseInt(console.readGameCommand());
        if (outSymbols.stream().map(ISymbol::getId).toList().contains(id)) {
            pastCoordinates = currentPlayer.getSymbolById(id).getCoordinates();
            currentPlayer.move(id, moveNumber);
            return id;
        }
        notifyObservers("Wrong id. You miss this opportunity.");
        return null;
    }

    private Integer rolledLessThan6Choices(ArrayList<ISymbol> outSymbols, IPlayer currentPlayer, int moveNumber) {
        if (!outSymbols.isEmpty()) {
            notifyObservers("You have " + outSymbols.size() + " symbol(s) out.");
            notifyObservers("Their ids are: " + outSymbols
                    .stream()
                    .map(ISymbol::getId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")) + ".");
            notifyObservers("Choose the symbol to move:");
            return move(outSymbols, currentPlayer, moveNumber);
        } else {
            notifyObservers("You have no moves! Moving on.");
            return null;
        }
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
