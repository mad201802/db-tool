package de.dbtool.console;

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

    public static void printVerbose(String message) {
        if (VERBOSE) {
            System.out.println(PREFIX + "[VERBOSE] " + message);
        }
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void printInfo(String message) {
        System.out.println(ANSI_YELLOW + PREFIX + "[INFO] " + message + ANSI_RESET);
    }

    public static void printError(String message) {
        System.err.println(ANSI_RED + PREFIX + "[ERROR] " + message + ANSI_RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(ANSI_GREEN + PREFIX + "[SUCCESS] " + message + ANSI_RESET);
    }
}
