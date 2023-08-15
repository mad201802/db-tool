package de.dbtool.database;

import de.dbtool.cli.subcommands.containers.*;

import java.util.List;

/**
 * This class represents a grep query to the database
 * It contains all the options that dictate what to search for
 */
public class Query {
    private final List<TablePatternOption> tablePatterns;
    private final List<TableRegexOption> tableRegex;
    private final List<ColumnPatternOption> columnPatterns;
    private final List<ColumnRegexOption> columnRegex;
    private final List<ValuePatternOption> valuePatterns;
    private final List<ValueCompareOption> valueCompares;
    private final boolean valueCompareUseAnd;
    private final int limitRows;

    /**
     * Constructor for the Query class
     * @param tablePatterns The table patterns to search for
     * @param tableRegexOptions The table regex options to search for
     * @param columnPatternOptions The column patterns to search for
     * @param columnRegexOptions The column regex options to search for
     * @param valuePatternOptions The value patterns to search for
     * @param valueCompareOptions The value compare options to search for
     * @param valueCompareUseAnd Whether to use AND or OR when combining the value compare options
     * @param limitRows The maximum number of rows to return
     */
    public Query(List<TablePatternOption> tablePatterns, List<TableRegexOption> tableRegexOptions,
                 List<ColumnPatternOption> columnPatternOptions, List<ColumnRegexOption> columnRegexOptions,
                 List<ValuePatternOption> valuePatternOptions, List<ValueCompareOption> valueCompareOptions,
                 boolean valueCompareUseAnd, int limitRows
    ) {
        this.tablePatterns = tablePatterns;
        this.tableRegex = tableRegexOptions;
        this.columnPatterns = columnPatternOptions;
        this.columnRegex = columnRegexOptions;
        this.valuePatterns = valuePatternOptions;
        this.valueCompares = valueCompareOptions;
        this.valueCompareUseAnd = valueCompareUseAnd;
        this.limitRows = limitRows;
    }

    public List<TablePatternOption> getTablePatterns() {
        return tablePatterns;
    }

    public List<TableRegexOption> getTableRegex() {
        return tableRegex;
    }

    public List<ColumnPatternOption> getColumnPatterns() {
        return columnPatterns;
    }

    public List<ColumnRegexOption> getColumnRegex() {
        return columnRegex;
    }

    public List<ValuePatternOption> getValuePatterns() {
        return valuePatterns;
    }

    public List<ValueCompareOption> getValueCompares() {
        return valueCompares;
    }

    public boolean isValueCompareUseAnd() {
        return valueCompareUseAnd;
    }
    public int getLimitRows() {
        return limitRows;
    }
}
