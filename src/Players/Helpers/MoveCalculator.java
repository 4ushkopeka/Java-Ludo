package Players.Helpers;

import java.util.ArrayList;

public final class MoveCalculator {

    private MoveCalculator() {}

    private static final ArrayList<CoordinateObject> turnPoints = new ArrayList<>() {{
        add(new CoordinateObject(6, 6));
        add(new CoordinateObject(0, 6));
        add(new CoordinateObject(0, 8));
        add(new CoordinateObject(6, 8));
        add(new CoordinateObject(6, 14));
        add(new CoordinateObject(8, 14));
        add(new CoordinateObject(8, 8));
        add(new CoordinateObject(14, 8));
        add(new CoordinateObject(14, 6));
        add(new CoordinateObject(8, 6));
        add(new CoordinateObject(8, 0));
        add(new CoordinateObject(6, 0));
        add(new CoordinateObject(7, 0));
        add(new CoordinateObject(0, 7));
        add(new CoordinateObject(7, 14));
        add(new CoordinateObject(14, 7));
    }};

    private static final ArrayList<CoordinateObject> centerPoints = new ArrayList<>() {{
        add(new CoordinateObject(6, 6));
        add(new CoordinateObject(6, 8));
        add(new CoordinateObject(8, 6));
        add(new CoordinateObject(8, 8));
    }};

    private static final ArrayList<String> directionsFromPoints = new ArrayList<>() {{
        add("up");
        add("right");
        add("down");
        add("right");
        add("down");
        add("left");
        add("down");
        add("left");
        add("up");
        add("left");
        add("up");
        add("right");
        add("right");
        add("down");
        add("left");
        add("up");
    }};

    private static String direction;

    /**
     * Calculates the exact coordinates a symbol is supposed to move to.
     * @param coordinates the current coordinates of the symbol
     * @param moveNumber the amount of moves the symbol must move
     * @param canCompleteTurn <code>true</code> if the symbol has made a lap, otherwise <code>false</code>
     * @return the deduced coordinates.
     */
    public static CoordinateObject calculate(CoordinateObject coordinates, int moveNumber, boolean canCompleteTurn) {
        direction = getDirection(coordinates);

        for (int i = 0; i < moveNumber; i++) {
            direction = polishDirection(coordinates, canCompleteTurn);

            switch (direction) {
                case "up" -> coordinates.incrementY();
                case "right" -> coordinates.incrementX();
                case "down" -> coordinates.decrementY();
                default -> coordinates.decrementX();
            }

            checkCenterTiles(coordinates);
        }

        return coordinates;
    }

    /**
     * This method checks the position of the symbol
     * and the center turning points. Upon a match, it will change
     * the direction and adjust the coordinates of the symbol as necessary.
     */
    private static void checkCenterTiles(CoordinateObject coordinates) {
        CoordinateObject match = centerPoints.stream()
                .filter(coords -> coords.equals(coordinates))
                .findFirst().orElse(null);

        if (match != null) {
            int index = turnPoints.indexOf(match);

            if (index == 0) {
                coordinates.incrementY();
            } else if (index == 1) {
                coordinates.incrementX();
            } else if (index == 2) {
                coordinates.decrementY();
            } else {
                coordinates.decrementX();
            }

            direction = directionsFromPoints.get(index);
        }
    }

    /**
     * This method is used to get the initial direction of the symbol.
     * @return a string, representing the direction.
     */
    private static String getDirection(CoordinateObject coordinates) {
        if (coordinates.getX() == 6 || coordinates.getX() == 0) {
            return "right";
        } else if (coordinates.getX() == 8 || coordinates.getX() == 14) {
            return "left";
        } else {
            if (coordinates.getY() == 6 || coordinates.getY() == 0) {
                return "up";
            } else  {
                return "down";
            }
        }
    }

    /**
     * This method is used to adjust the direction based on turning points.
     * It also considers whether a symbol is ready to turn for completion as well,
     * changing the direction on certain tiles only if the symbol has made a full lap.
     * @return a string, representing the direction.
     */
    private static String polishDirection(CoordinateObject coordinates, boolean canCompleteTurn) {
        CoordinateObject match = turnPoints.stream()
                .filter(coords -> coords.equals(coordinates))
                .findFirst().orElse(null);

        if (match != null) {
            int index = turnPoints.indexOf(match);

            if(canCompleteTurn) {
                return directionsFromPoints.get(index);
            } else {
                if(index > 11) {
                    return  direction;
                } else {
                    return directionsFromPoints.get(index);
                }
            }
        } else {
            return direction;
        }
    }
}
