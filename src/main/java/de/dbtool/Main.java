package de.dbtool;

import de.dbtool.cli.DbToolCommand;
import de.dbtool.utils.ASCIIArt;
import io.micronaut.configuration.picocli.PicocliRunner;


public class Main {
    public static String TOOL_VERSION = "0.0.1";

    public static void main(String[] args) {
        ASCIIArt.printLogo();
        PicocliRunner.run(DbToolCommand.class, args);
    }
}
