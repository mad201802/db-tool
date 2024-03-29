package de.dbtool.cli.subcommands.containers;

import picocli.CommandLine;

/**
 * Class to handle the table pattern option
 */
public class TablePatternOption implements IBaseOption {
    @CommandLine.Option(names = {"-t", "-tp", "--table-pattern"}, description = "Define wildcard (like) pattern for table names")
    private String tablePattern;

    @Override
    public String getOption() {
        return tablePattern;
    }
}
