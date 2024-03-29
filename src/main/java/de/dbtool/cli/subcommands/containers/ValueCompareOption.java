package de.dbtool.cli.subcommands.containers;

import picocli.CommandLine;

/**
 * Class to handle the value compare option (>, <, >=, <=, !=, =)
 */
public class ValueCompareOption implements IBaseOption {
    @CommandLine.Option(names = {"-vc", "--value-compare"}, description = "Compare a Number or Date using common operators like <,>,=,!=,<=,>= e.g. 'id > 100'")
    private String valueCompare;

    @Override
    public String getOption() {
        return valueCompare;
    }
}
