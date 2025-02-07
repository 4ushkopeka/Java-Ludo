package Console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * This class aims to group the spread out console functions into one.
 */
public class Console {
    private final BufferedReader reader;

    public Console() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Prints a line of text to the console.
     */
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Reads a line of text from the console.
     * <p>
     * Note: All input received from the console is in string.
     * </p>
     */
    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Prints a 2-dimensional string matrix to the console.
     */
    public void printMatrix(String[][] board) {
        for (String[] strings : board) {
            for (String string : strings) System.out.print(string);
            System.out.println();
        }
    }
}

