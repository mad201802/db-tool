package de.dbtool.console;

/**
 * Simplified logging methods for different log levels with color and prefixes
 */
public class ConsolePrinter {
    private static final String PREFIX = "DB-Tool > ";
    public static boolean VERBOSE = false;

    /**
     * Print a verbose (debugging) message, that will only be visible in verbose mode (using --please-tell-me-everything)
     * @param message the debugging message to print
     */
    public static void printVerbose(String message) {
        if (VERBOSE) {
            System.out.println(PREFIX + message);
        }
    }

    /**
     * Print a simple message with prefix
     * @param message The Message to display
     */
    public static void print(String message) {
        System.out.println(PREFIX + message);
    }

    /**
     * Print an info message
     * @param message The info message
     */
    public static void printInfo(String message) {
        System.out.println(PREFIX + "[INFO] " + message);
    }

    /**
     * Print an error message
     * @param message the error message
     */
    public static void printError(String message) {
        System.out.println(PREFIX + "[ERROR] " + message);
    }
}
