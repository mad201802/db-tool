package de.dbtool.database;

import de.dbtool.cli.subcommands.containers.ColumnPatternOption;
import de.dbtool.cli.subcommands.containers.ColumnRegexOption;
import de.dbtool.cli.subcommands.containers.TablePatternOption;
import de.dbtool.cli.subcommands.containers.TableRegexOption;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.SearchUtils;

import java.util.*;
import java.util.stream.Collectors;

public class QueryProcessor {
    private Query query;

    private final IDatabase db;
    private final Map<String, Map<String, List<String>>> values = new HashMap<>();

    public QueryProcessor(IDatabase db, Query query) throws DbToolException {
        this.db = db;
        this.query = query;
    }

    public List<String[]> executeQuery() throws DbToolException {
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

        for (String t : tables) {
            List<List<String>> res = db.getValues(
                    t, columns.get(t),
                    query.getValuePatterns() != null ? query.getValuePatterns().stream().map(v -> v.getOption().replace("*", "%")).collect(Collectors.toList()) : new ArrayList<>(),
                    query.getValueCompares() != null ? query.getValueCompares().stream().map(v -> v.getOption()).collect(Collectors.toList()) : new ArrayList<>(),
                    Optional.empty()
            );

            // TODO actually print results
            System.out.println("Table " + t + ": " + res.size() + " Row(s) found");
            System.out.println("=====================================================================================");
            for (List<String> row : res) {
                System.out.println(String.join(", ", row));
            }
            System.out.println("=====================================================================================");
            System.out.println("");

//            List<String[]> tableData = new ArrayList<>(res.size());
//            tableData.add(new String[] {"Name", "Hostname", "Port", "Database", "Username", "Password", "Type", "Driver"});
//            for(Profile p : profiles) {
//                tableData.add(new String[] {p.name, p.hostname, Integer.toString(p.port), p.dbName, p.username, tablePrinter.censorPassword(p.password), p.type.toString(), p.driverPath});
//            }
//            System.out.println(t + ":");
            // | TableName | ColumnName | Value |
            // -----------------------------------
            // | Mensch    | Name       | Andre |
            // | Mixer     | Leistung   | 50    |
            //
        }

        for (String currentTable : tables) {
            Set<String> currentColumns = columns.get(currentTable);
            for(String column : currentColumns) {
                if (query.getValuePatterns() != null) {
                    //TODO
                }

                if (query.getValueCompares() != null) {
                    //TODO
                }

                if (query.getValueRegex() != null) {
                    // TODO
                }

                if (query.getColumnRegex() == null && query.getColumnPatterns() == null) {
                    // TODO
                }
            }
        }

//        System.out.println(tables.toString());
//        System.out.println(columns.toString());

        return null;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

}
