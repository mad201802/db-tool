package de.dbtool.cli.subcommands.containers;

import picocli.CommandLine;

/**
 * Class to handle the value regex option
 */
public class ValueCompareOption implements IBaseOption {
    @CommandLine.Option(names = {"-vc", "--value-compare"}, description = "Compare a Number or Date using common operators like <,>,=,!=,<=,>=")
    private String valueCompare;

    @Override
    public String getOption() {
        return valueCompare;
    }
}
