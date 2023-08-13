package de.dbtool.utils;

public class ASCIIArt {

    /**
     * Prints the ASCII art logo
     */
    public static void printLogo() {
        System.out.println("\n" +
                "  _____  ____     _______          _ \n" +
                " |  __ \\|  _ \\   |__   __|        | |\n" +
                " | |  | | |_) |_____| | ___   ___ | |\n" +
                " | |  | |  _ <______| |/ _ \\ / _ \\| |\n" +
                " | |__| | |_) |     | | (_) | (_) | |\n" +
                " |_____/|____/      |_|\\___/ \\___/|_|\n" +
                "                                     \n" +
                "                                     \n" +
                "Version: " + de.dbtool.Main.TOOL_VERSION + "\n" +
                "The all-in-one tool to explore and search through databases\n" +
                "Made with <3 by Leonard Laise, Michael Dick and Tom Flocken\n");
    }

    public static void handleDriverName(String name) {
        if (name.toLowerCase().contains("oracle")) {
            System.out.println("\n" +
                    "=================================================================================================================\n" +
                    "= We, the developers of this tool, hate OracleDB and are not recommending using it. Nevertheless continuing ... =\n" +
                    "=================================================================================================================\n");
        }
    }
}
