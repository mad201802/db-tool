package de.dbtool.cli.subcommands.containers;

import picocli.CommandLine;

/**
 * Class to handle the column pattern option
 */
public class ColumnPatternOption implements IBaseOption {
    @CommandLine.Option(names = {"-c", "-cp", "--column-pattern"}, description = "Define a wildcard (like) pattern for column names")
    private String columnPattern;

    @Override
    public String getOption() {
        return columnPattern;
    }
}
