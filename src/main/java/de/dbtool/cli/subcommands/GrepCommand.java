package de.dbtool.cli.subcommands;

import de.dbtool.cli.subcommands.containers.*;
import picocli.CommandLine;

import java.util.List;

/**
 * Subcommand to search for patterns/regex in a database
 */
@CommandLine.Command(name = "grep", description = "Grep-like tool for searching a database", mixinStandardHelpOptions = true)
public class GrepCommand implements Runnable {

    @CommandLine.Option(names = {"-p", "--profile"}, description = "The name of the profile", required = true)
    private String profileName;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table name pattern options")
    private List<TablePatternOption> tablePatternOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table name regex options")
    private List<TableRegexOption> tableRegexOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Column name pattern options")
    private List<ColumnPatternOption> columnPatternOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Column name regex options")
    private List<ColumnRegexOption> columnRegexOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table content/value pattern options")
    private List<ValuePatternOption> valuePatternOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table content/value regex options")
    private List<ValueRegexOption> valueRegexOptions;

    @CommandLine.Option(names = {"-r", "--range"}, description = "Search for a range in table values", required = false)
    private String rangeQuery;

    @CommandLine.Option(names = {"-lc", "--limit-columns"}, description = "Limits the number of columns to display", required = false)
    private String limitColumnsQuery;

    @CommandLine.Option(names = {"-lr", "--limit-rows"}, description = "Limits the number of rows to display", required = false)
    private int limitRows;

    @CommandLine.Option(names = {"-lt", "--limit-text-length"}, description = "Limits the length of text in a column and display ellipsis", required = false)
    private int limitTextLength;

    @Override
    public void run() {
        // TODO: Implement
    }
}
