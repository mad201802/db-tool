package de.dbtool.database;

import de.dbtool.cli.subcommands.containers.ColumnPatternOption;
import de.dbtool.cli.subcommands.containers.ColumnRegexOption;
import de.dbtool.cli.subcommands.containers.TablePatternOption;
import de.dbtool.cli.subcommands.containers.TableRegexOption;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.utils.SearchUtils;

import java.util.*;

public class QueryProcessor {
    private Query query;

    private final IDatabase db;

    private final Set<String> tables = new HashSet<>();
    private final Map<String, Set<String>> columns = new HashMap<>();

    public QueryProcessor(IDatabase db, Query query) throws DbToolException {
        this.db = db;
        this.query = query;
    }

    public List<String[]> executeQuery() throws DbToolException {
        if (query == null) throw new RuntimeException("Query is null");
        this.db.connect();

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

        System.out.println(tables.toString());
        System.out.println(columns.toString());

        return null;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

}
