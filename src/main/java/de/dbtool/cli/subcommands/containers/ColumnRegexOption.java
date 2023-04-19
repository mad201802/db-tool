package de.dbtool.cli.subcommands.containers;

import picocli.CommandLine;

/**
 * Class to handle the column pattern option
 */
public class ColumnRegexOption implements IBaseOption {
    @CommandLine.Option(names = {"-cr", "--column-regex"}, description = "Define regex pattern for column names")
    private String columnRegex;

    @Override
    public String getOption() {
        return columnRegex;
    }
}
