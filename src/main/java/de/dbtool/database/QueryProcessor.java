package de.dbtool.database;

import de.dbtool.cli.subcommands.containers.ColumnPatternOption;
import de.dbtool.cli.subcommands.containers.ColumnRegexOption;
import de.dbtool.cli.subcommands.containers.TablePatternOption;
import de.dbtool.cli.subcommands.containers.TableRegexOption;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.utils.SearchUtils;

import java.util.*;
import java.util.regex.Pattern;

public class QueryProcessor {
    private Query query;

    private final IDatabase db;

    private Set<String> tables = new HashSet<>();
    private Map<String, Set<String>> columns = new HashMap<>();

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

            columns.put(table, new HashSet<>());
            List<String> columnNames = this.db.getTableColumns(table);

            if (query.getColumnPatterns() != null) {
                for (ColumnPatternOption columnPattern : query.getColumnPatterns()) {
                    String regex = String.join(".*", Arrays.stream(columnPattern.getOption().split("\\*")).map(Pattern::quote).toList());
                    try {
                        columns.get(table).addAll(SearchUtils.getMatchingStrings(columnNames, regex));
                    } catch (Exception e) {
                        throw new DbToolException("Something is wrong with your regex: " + e.getMessage());
                    }
                }
            }

            if (query.getTableRegex() != null) {
                for (ColumnRegexOption regex : query.getColumnRegex()) {
                    try {
                        columns.get(table).addAll(SearchUtils.getMatchingStrings(columnNames, regex.getOption()));
                    } catch (Exception e) {
                        throw new DbToolException("Something is wrong with your regex: " + e.getMessage());
                    }
                }
            }

            if (query.getColumnRegex() == null && query.getColumnPatterns() == null) {
                // TODO: add all columns? or just detect this case later and perform a `select * from ...`
            }
        }

        for (String table : tables) {
            for (String column : columns.get(table)) {
                List<String> values = this.db.getColumnValues(table, column);
            }
        }

        System.out.println(tables.toString());

        return null;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

}
