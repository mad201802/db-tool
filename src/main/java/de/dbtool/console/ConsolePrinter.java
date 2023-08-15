package de.dbtool.console;

/**
 * Simplified logging methods for different log levels with color and prefixes
 */
public class ConsolePrinter {
    private static final String PREFIX = "DB-Tool > ";
    public static boolean VERBOSE = false;

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";


    /**
     * Print a verbose (debugging) message, that will only be visible in verbose mode (using --please-tell-me-everything)
     * @param message the debugging message to print
     */
    public static void printVerbose(String message) {
        if (VERBOSE) {
            System.out.println(PREFIX + "[VERBOSE] " + message);
        }
    }

    /**
     * Print a simple message with prefix
     * @param message The Message to display
     */
    public static void print(String message) {
        System.out.println(message);
    }

    /**
     * Print an info message
     * @param message The info message
     */
    public static void printInfo(String message) {
        System.out.println(ANSI_YELLOW + PREFIX + "[INFO] " + message + ANSI_RESET);
    }

    /**
     * Print an error message
     * @param message the error message
     */
    public static void printError(String message) {
        System.err.println(ANSI_RED + PREFIX + "[ERROR] " + message + ANSI_RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(ANSI_GREEN + PREFIX + "[SUCCESS] " + message + ANSI_RESET);
    }
}
