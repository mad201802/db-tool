package de.dbtool.database;

import de.dbtool.cli.subcommands.containers.*;

import java.util.List;

public class Query {
    private List<TablePatternOption> tablePatterns;
    private List<TableRegexOption> tableRegex;
    private List<ColumnPatternOption> columnPatterns;
    private List<ColumnRegexOption> columnRegex;
    private List<ValuePatternOption> valuePatterns;
    private List<ValueCompareOption> valueCompares;
    private List<ValueRegexOption> valueRegex;

    public Query(List<TablePatternOption> tablePatterns, List<TableRegexOption> tableRegexOptions,
                 List<ColumnPatternOption> columnPatternOptions, List<ColumnRegexOption> columnRegexOptions,
                 List<ValuePatternOption> valuePatternOptions, List<ValueCompareOption> valueCompareOptions,
                 List<ValueRegexOption> valueRegexOptions
    ) {
        this.tablePatterns = tablePatterns;
        this.tableRegex = tableRegexOptions;
        this.columnPatterns = columnPatternOptions;
        this.columnRegex = columnRegexOptions;
        this.valuePatterns = valuePatternOptions;
        this.valueCompares = valueCompareOptions;
        this.valueRegex = valueRegexOptions;
    }

    public Query() {
    }

    public List<TablePatternOption> getTablePatterns() {
        return tablePatterns;
    }

    public void setTablePatterns(List<TablePatternOption> tablePatterns) {
        this.tablePatterns = tablePatterns;
    }

    public List<TableRegexOption> getTableRegex() {
        return tableRegex;
    }

    public void setTableRegex(List<TableRegexOption> tableRegex) {
        this.tableRegex = tableRegex;
    }

    public List<ColumnPatternOption> getColumnPatterns() {
        return columnPatterns;
    }

    public void setColumnPatterns(List<ColumnPatternOption> columnPatterns) {
        this.columnPatterns = columnPatterns;
    }

    public List<ColumnRegexOption> getColumnRegex() {
        return columnRegex;
    }

    public void setColumnRegex(List<ColumnRegexOption> columnRegex) {
        this.columnRegex = columnRegex;
    }

    public List<ValuePatternOption> getValuePatterns() {
        return valuePatterns;
    }

    public List<ValueRegexOption> getValueRegex() {
        return valueRegex;
    }

    public void setValueRegex(List<ValueRegexOption> valueRegex) {
        this.valueRegex = valueRegex;
    }

    public List<ValueCompareOption> getValueCompares() {
        return valueCompares;
    }

    public void setValueCompares(List<ValueCompareOption> valueCompares) {
        this.valueCompares = valueCompares;
    }
}
