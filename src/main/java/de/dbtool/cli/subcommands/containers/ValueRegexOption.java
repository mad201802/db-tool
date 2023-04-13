package de.dbtool.cli.subcommands.containers;

import picocli.CommandLine;

/**
 * Class to handle the value regex option
 */
public class ValueRegexOption implements IBaseOption {
    @CommandLine.Option(names = {"-vr", "--value-regex"}, description = "Define regex pattern for table values")
    private String valueRegex;

    @Override
    public String getOption() {
        return valueRegex;
    }
}
