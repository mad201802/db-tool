package de.dbtool.console;

public class ConsolePrinter {
    private static final String PREFIX = "DB-Tool > ";
    public static boolean VERBOSE = false;

    public static void printVerbose(String message) {
        if (VERBOSE) {
            System.out.println(PREFIX + message);
        }
    }

    public static void print(String message) {
        System.out.println(PREFIX + message);
    }

    public static void printInfo(String message) {
        System.out.println(PREFIX + "[INFO] " + message);
    }

    public static void printError(String message) {
        System.out.println(PREFIX + "[ERROR] " + message);
    }
}
