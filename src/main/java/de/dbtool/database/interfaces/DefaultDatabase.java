package de.dbtool.database.interfaces;

import de.dbtool.cli.subcommands.options.SupportedDatabases;
import de.dbtool.drivers.JDBCDriverLoader;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.ASCIIArt;

import java.sql.*;
import java.util.*;

public class DefaultDatabase implements IDatabase {

    private final Profile profile;
    private String databaseType;

    private Connection connection;

    public DefaultDatabase(Profile profile) throws DbToolException {
        this.profile = profile;
        this.loadDriverIfNecessary();
    }

    @Override
    public void connect() throws DbToolException {
        String url = "";
        Properties connectionProps = new Properties();

        if(profile.type == SupportedDatabases.SQLITE) {
            System.out.println("Reading database: " + profile.hostname);
            url = "jdbc:sqlite:" + profile.hostname;
        } else {
            System.out.println("Connecting to database: " + profile.hostname + ":" + profile.port + "/" + profile.dbName);
            connectionProps.put("user", profile.username);
            connectionProps.put("password", profile.password);

            url = "jdbc:" + this.databaseType + "://" + profile.hostname + ":" + profile.port + "/" + profile.dbName;
        }

        try {
            connection = DriverManager.getConnection(url, connectionProps);
            System.out.println("Successfully connected to database!");
        } catch (SQLException e) {
            throw new DbToolException("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public List<String> getAllDatabaseTables() throws DbToolException {
        return this.getDatabaseTables("%");
    }

    @Override
    public List<String> getDatabaseTables(String pattern) throws DbToolException {
        List<String> returnList = new ArrayList<>();
        try {
            DatabaseMetaData dbmd = this.connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, pattern, types);
            while (rs.next()) {
                returnList.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new DbToolException("Error getting tables: " + e.getMessage());
        }

        return returnList;
    }

    @Override
    public List<String> getTableColumns(String tableName) throws DbToolException {
        List<String> returnList = new ArrayList<>();
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
            while (resultSet.next()) {
                returnList.add(resultSet.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            throw new DbToolException("Error getting columns: " + e.getMessage());
        }

        return returnList;
    }

    @Override
    public List<String> getTableColumns(String tableName, String pattern) throws DbToolException {
        List<String> returnList = new ArrayList<>();
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getColumns(null, null, tableName, pattern);
            while (resultSet.next()) {
                returnList.add(resultSet.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            throw new DbToolException("Error getting columns: " + e.getMessage());
        }
        return returnList;
    }

    @Override
    public List<List<String>> getValues(String table, Set<String> columns, List<String> patterns, List<String> compares, boolean useAnd, Optional<Integer> limitRows) throws DbToolException {
        final String columnSelection = columns.isEmpty() ? "*" : String.join(", ", columns);

        StringBuilder query = new StringBuilder();
        query.append("select ").append(columnSelection).append(" from ").append(table);

        List<String> patternClauses = new ArrayList<>();
        List<String> compareClauses = new ArrayList<>();

        // Generate where clause
        if(patterns != null) {
            for (String col : columns) {
                for (String pattern : patterns) {
                    patternClauses.add(col + " LIKE " + "'" + pattern + "'");
                }
            }
        }

        if(compares != null) {
            for (String compare : compares) {
                if (compare.matches("^\\s*[!=<>]")) {
                    for (String col : columns) {
                        compareClauses.add(col + compare);
                    }
                } else {
                    compareClauses.add(compare);
                }
            }
        }

        if(patternClauses.size() > 0 || compareClauses.size() > 0) {
            query.append(" where ");
            String joiner = useAnd ? " AND " : " OR ";

            if(patternClauses.size() > 0) {
                query.append(String.join(joiner, patternClauses));
            }

            if(compareClauses.size() > 0) {
                query.append(String.join(joiner, compareClauses));
            }
        }

        // add limit
        limitRows.ifPresent(integer -> query.append(" LIMIT ").append(integer));

        System.out.println("Executing query: " + query.toString());

        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query.toString());
            ResultSetMetaData rsmd = rs.getMetaData();
            int n_cols = rsmd.getColumnCount();

            List<List<String>> results = new ArrayList<>();

            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= n_cols; i++) {
                    row.add(rs.getString(i));
                }
                results.add(row);
            }
            return results;

        } catch (SQLException e) {
            throw new DbToolException("SQL Error occurred: " + e.getMessage());
        }
    }

    private void loadDriverIfNecessary() throws DbToolException {
        if (profile.type == SupportedDatabases.OTHER) {
            System.out.println("Loading driver: " + profile.driverPath);
            try {
                Driver driver = JDBCDriverLoader.loadDriver(profile.driverPath);
                ASCIIArt.handleDriverName(driver.toString());
                DriverManager.registerDriver(driver);
                this.databaseType = driver.toString().split("\\.")[1];
                System.out.println("Driver for " + this.databaseType.toUpperCase() + " loaded");
                return;
            } catch (SQLException e) {
                throw new DbToolException("Error loading driver: " + e.getMessage());
            }
        }
        this.databaseType = profile.type.name().toLowerCase();
    }

}
