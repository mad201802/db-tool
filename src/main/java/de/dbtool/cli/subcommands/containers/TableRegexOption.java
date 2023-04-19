package de.dbtool.cli.subcommands.containers;

import picocli.CommandLine;

/**
 * Class to handle the table regex option
 */
public class TableRegexOption implements IBaseOption {
    @CommandLine.Option(names = {"-tr", "--table-regex"}, description = "Define regex pattern for table names")
    private String tableRegex;

    public String getOption() {
        return tableRegex;
    }
}
