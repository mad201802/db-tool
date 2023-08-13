package de.dbtool.cli.subcommands.containers;

import picocli.CommandLine;

/**
 * Class to handle the value pattern option
 */
public class ValuePatternOption implements IBaseOption {
    @CommandLine.Option(names = {"-v", "-vp", "--value-pattern"}, description = "Define wildcard (like) pattern for table values")
    private String valuePattern;

    @Override
    public String getOption() {
        return valuePattern;
    }
}
