package de.dbtool.database;

import de.dbtool.cli.subcommands.containers.*;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.utils.SearchUtils;

import java.util.*;
import java.util.stream.Collectors;

public class QueryProcessor {
    private Query query;

    private final IDatabase db;

    public QueryProcessor(IDatabase db, Query query) throws DbToolException {
        this.db = db;
        this.query = query;
    }

    public Map<String, List<String[]>> executeQuery() throws DbToolException {
        if (query == null) throw new RuntimeException("Query is null");
        this.db.connect();

        Set<String> tables = new HashSet<>();
        Map<String, Set<String>> columns = new HashMap<>();

        if(query.getTablePatterns() != null) {
            for(TablePatternOption tablePattern : query.getTablePatterns()) {
                String likePattern = tablePattern.getOption().replace("*", "%");
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

        // Find matching column names
        for (String table : tables) {
            Set<String> columnNames = new HashSet<>();

            if (query.getColumnPatterns() != null) {
                for (ColumnPatternOption columnPattern : query.getColumnPatterns()) {
                    String likePattern = columnPattern.getOption().replace("*", "%");
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

        Map<String, List<String[]>> result = new HashMap<>();
        for (String t : tables) {
            if(columns.get(t).size() == 0) continue;

            List<List<String>> values = db.getValues(
                    t, columns.get(t),
                    query.getValuePatterns() != null ? query.getValuePatterns().stream().map(v -> v.getOption().replace("*", "%")).collect(Collectors.toList()) : new ArrayList<>(),
                    query.getValueCompares() != null ? query.getValueCompares().stream().map(ValueCompareOption::getOption).collect(Collectors.toList()) : new ArrayList<>(),
                    query.isValueCompareUseAnd(),
                    query.getLimitRows() > 0 ? Optional.of(query.getLimitRows()) : Optional.empty()
            );

            List<String[]> tableData = new ArrayList<>();
            tableData.add(columns.get(t).toArray(new String[0]));
            for(List<String> row : values) {
                tableData.add(row.toArray(new String[0]));
            }

            // TODO: Implement --no-trunctate option (add second case where Optional.empty() has been replaced
            //  with Optional.of(0) or something like that because 0 means no truncation)
            result.put(t, tableData);
        }

        return result;
    }
}
