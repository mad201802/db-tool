package de.dbtool.database;

import de.dbtool.cli.subcommands.containers.*;
import de.dbtool.console.ConsolePrinter;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.utils.SearchUtils;
import de.dbtool.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to execute a Query object on a database
 */
public class QueryProcessor {

    /** The query to execute */
    private final Query query;

    /** The database to execute the query on */
    private final IDatabase db;

    /**
     * Constructor for the QueryProcessor class
     * @param db The database to execute the query on
     * @param query The query to execute
     * @throws DbToolException If an error occurs while connecting to the database
     */
    public QueryProcessor(IDatabase db, Query query) throws DbToolException {
        this.db = db;
        this.query = query;
    }

    /**
     * Execute the query on the database
     * @return A map of table names to a list of rows that match the query
     * @throws DbToolException If an error occurs while executing the query
     */
    public Map<String, List<String[]>> executeQuery() throws DbToolException {
        if (query == null) throw new RuntimeException("Query is null");

        this.db.connect();

        // Build a list of matching table names and column names
        Set<String> tables = new HashSet<>();
        Map<String, Set<String>> columns = new HashMap<>();

        // Find matching table names
        if(query.getTablePatterns() != null) {
            for(TablePatternOption tablePattern : query.getTablePatterns()) {
                String likePattern = Utils.toLikePattern(tablePattern.getOption());
                tables.addAll(db.getDatabaseTables(likePattern));
            }
        }

        if(query.getTableRegex() != null) {
            List<String> allTables = this.db.getAllDatabaseTables();
            for(TableRegexOption tableRegex : query.getTableRegex()) {
                try {
                    tables.addAll(SearchUtils.getMatchingStrings(allTables, tableRegex.getOption()));
                } catch (Exception e) {
                    throw new DbToolException("Something is wrong with your regex: " + e.getMessage());
                }
            }
        }

        if(query.getTableRegex() == null && query.getTablePatterns() == null) {
            tables.addAll(db.getAllDatabaseTables());
        }

        // Find matching column names for each table
        for (String table : tables) {
            Set<String> columnNames = new HashSet<>();

            if (query.getColumnPatterns() != null) {
                for (ColumnPatternOption columnPattern : query.getColumnPatterns()) {
                    String likePattern = Utils.toLikePattern(columnPattern.getOption());
                    columnNames.addAll(db.getTableColumns(table, likePattern));
                }
            }

            if (query.getColumnRegex() != null) {
                List<String> allColumns = this.db.getTableColumns(table);

                for (ColumnRegexOption regex : query.getColumnRegex()) {
                    try {
                        columnNames.addAll(SearchUtils.getMatchingStrings(allColumns, regex.getOption()));
                    } catch (Exception e) {
                        throw new DbToolException("Something is wrong with your regex: " + e.getMessage());
                    }
                }
            }

            if (query.getColumnRegex() == null && query.getColumnPatterns() == null) {
                columnNames.addAll(db.getTableColumns(table));
            }

            columns.put(table, columnNames);
        }

        // Execute the query on each table and accumulate the results
        Map<String, List<String[]>> result = new HashMap<>();

        for (String t : tables) {
            if(columns.get(t).size() == 0) continue;

            List<String> valuePatterns = query.getValuePatterns() != null
                ? query.getValuePatterns().stream()
                    .map(ValuePatternOption::getOption)
                    .map(Utils::toLikePattern)
                    .toList()
                : new ArrayList<>();

            List<String> valueCompares = query.getValueCompares() != null
                ? query.getValueCompares().stream()
                    .map(ValueCompareOption::getOption)
                    .toList()
                : new ArrayList<>();

            try {
                List<List<String>> values = db.getValues(
                        t, columns.get(t),
                        valuePatterns, valueCompares,
                        query.isValueCompareUseAnd(),
                        query.getLimitRows() > 0 ? query.getLimitRows() : null
                );
                if(values.size() == 0) continue;

                List<String[]> tableData = new ArrayList<>();

                tableData.add(columns.get(t).toArray(new String[0]));
                tableData.addAll(values.stream().map(row -> row.toArray(new String[0])).toList());

                result.put(t, tableData);

            } catch (DbToolException e) {
                ConsolePrinter.printVerbose("Skipping table '" + t + "': " + e.getMessage());
            }
        }

        return result;
    }
}
