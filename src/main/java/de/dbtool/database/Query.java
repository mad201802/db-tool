package de.dbtool.database;

import de.dbtool.cli.subcommands.containers.*;

import java.util.List;

public class Query {
    private final List<TablePatternOption> tablePatterns;
    private final List<TableRegexOption> tableRegex;
    private final List<ColumnPatternOption> columnPatterns;
    private final List<ColumnRegexOption> columnRegex;
    private final List<ValuePatternOption> valuePatterns;
    private final List<ValueCompareOption> valueCompares;
    private final List<ValueRegexOption> valueRegex;
    private final int limitRows;

    public Query(List<TablePatternOption> tablePatterns, List<TableRegexOption> tableRegexOptions,
                 List<ColumnPatternOption> columnPatternOptions, List<ColumnRegexOption> columnRegexOptions,
                 List<ValuePatternOption> valuePatternOptions, List<ValueCompareOption> valueCompareOptions,
                 List<ValueRegexOption> valueRegexOptions, int limitRows
    ) {
        this.tablePatterns = tablePatterns;
        this.tableRegex = tableRegexOptions;
        this.columnPatterns = columnPatternOptions;
        this.columnRegex = columnRegexOptions;
        this.valuePatterns = valuePatternOptions;
        this.valueCompares = valueCompareOptions;
        this.valueRegex = valueRegexOptions;
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

    public List<ValueRegexOption> getValueRegex() {
        return valueRegex;
    }

    public List<ValueCompareOption> getValueCompares() {
        return valueCompares;
    }

    public int getLimitRows() {
        return limitRows;
    }
}
