package qqClientUtils;

/**
 * Utility class purpose:
 * Handle various user inputs and obtain user console input according to the programmer's needs.
 */
import java.util.Scanner;

public class Utility {
    // Static property...
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Function: Read a menu option from keyboard input, within the range 1—5
     * @return 1—5
     */
    public static char readMenuSelection() {
        char c;
        for (;;) {
            String str = readKeyBoard(1, false); // Contains a single character string
            c = str.charAt(0); // Convert string to char type
            if (c != '1' && c != '2' && 
                c != '3' && c != '4' && c != '5') {
                System.out.print("Invalid selection, please re-enter:");
            } else break;
        }
        return c;
    }

    /**
     * Function: Read a single character from keyboard input
     * @return a character
     */
    public static char readChar() {
        String str = readKeyBoard(1, false); // Just one character
        return str.charAt(0);
    }

    /**
     * Function: Read a single character from keyboard input; if enter key is pressed directly, return a specified default value; otherwise, return the input character.
     * @param defaultValue The specified default value
     * @return default value or input character
     */
    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, true); // Either an empty string or one character
        return (str.length() == 0) ? defaultValue : str.charAt(0);
    }

    /**
     * Function: Read an integer from keyboard input, length less than 2 digits
     * @return integer
     */
    public static int readInt() {
        int n;
        for (;;) {
            String str = readKeyBoard(10, false); // An integer, length <= 10 digits
            try {
                n = Integer.parseInt(str); // Convert string to integer
                break;
            } catch (NumberFormatException e) {
                System.out.print("Numeric input error, please re-enter:");
            }
        }
        return n;
    }

    /**
     * Function: Read an integer or default value from keyboard input; if enter key is pressed directly, return default value; otherwise, return the input integer.
     * @param defaultValue The specified default value
     * @return integer or default value
     */
    public static int readInt(int defaultValue) {
        int n;
        for (;;) {
            String str = readKeyBoard(10, true);
            if (str.equals("")) {
                return defaultValue;
            }

            // Exception handling...
            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Numeric input error, please re-enter:");
            }
        }
        return n;
    }

    /**
     * Function: Read a string of specified length from keyboard input
     * @param limit The length limit
     * @return string of the specified length
     */
    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    /**
     * Function: Read a string of specified length or default value from keyboard input; if enter key is pressed directly, return default value; otherwise, return the string.
     * @param limit The length limit
     * @param defaultValue The specified default value
     * @return string of the specified length
     */
    public static String readString(int limit, String defaultValue) {
        String str = readKeyBoard(limit, true);
        return str.equals("") ? defaultValue : str;
    }

    /**
     * Function: Read a confirmation option from keyboard input, Y or N
     * Encapsulate small functionalities into a method.
     * @return Y or N
     */
    public static char readConfirmSelection() {
        System.out.println("Please enter your choice (Y/N): Choose carefully");
        char c;
        for (;;) { // Infinite loop
            // Here, the received character is converted to uppercase
            // y => Y n=>N
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.print("Invalid selection, please re-enter:");
            }
        }
        return c;
    }

    /**
     * Function: Read a string
     * @param limit The length to read
     * @param blankReturn If true, empty strings can be read; if false, empty strings cannot be read.
     * 
     * If the input is empty or the input length exceeds the limit, the user will be prompted to re-enter.
     * @return the string that was read
     */
    private static String readKeyBoard(int limit, boolean blankReturn) {

        // Defined a string
        String line = "";

        // scanner.hasNextLine() checks if there is another line to read
        while (scanner.hasNextLine()) {
            line = scanner.nextLine(); // Reads the line

            // If line.length == 0, i.e., the user did not enter any content and just pressed enter
            if (line.length() == 0) {
                if (blankReturn) return line; // If blankReturn is true, can return an empty string
                else continue; // If blankReturn is false, cannot accept an empty string, must input content
            }

            // If the user's input exceeds the limit, prompt to re-enter
            // If the user's input length is >0 and <= limit, it is accepted
            if (line.length() < 1 || line.length() > limit) {
                System.out.print("Input length error (cannot exceed " + limit + "), please re-enter:");
                continue;
            }
            break;
        }

        return line;
    }
    
}

