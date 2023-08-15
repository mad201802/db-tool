package de.dbtool.database.interfaces;

import de.dbtool.cli.subcommands.options.SupportedDatabases;
import de.dbtool.console.ConsolePrinter;
import de.dbtool.drivers.JDBCDriverLoader;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.ASCIIArt;

import java.sql.*;
import java.util.*;

/**
 * Default implementation of the IDatabase interface
 * This class uses JDBC to connect to the database
 * It provides all necessary methods to query information from the database
 */
public class DefaultDatabase implements IDatabase {

    /** The profile to connect to */
    private final Profile profile;
    private String databaseType;

    /** The jdbc connection to the database */
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
            ConsolePrinter.printInfo("Reading database: " + profile.hostname);
            url = "jdbc:sqlite:" + profile.hostname;
        } else {
            ConsolePrinter.printInfo("Connecting to database: " + profile.hostname + ":" + profile.port + "/" + profile.dbName);
            connectionProps.put("user", profile.username);
            connectionProps.put("password", profile.password);

            url = "jdbc:" + this.databaseType + "://" + profile.hostname + ":" + profile.port + "/" + profile.dbName;
        }

        try {
            connection = DriverManager.getConnection(url, connectionProps);
            ConsolePrinter.printSuccess("Successfully connected to database!");
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

        ConsolePrinter.printVerbose("Executing query: " + query.toString());

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

    /**
     * Check if the database profile uses a custom driver and load it if necessary
     * @throws DbToolException If an error occurs while loading the driver
     */
    private void loadDriverIfNecessary() throws DbToolException {
        if (profile.type == SupportedDatabases.OTHER) {
            ConsolePrinter.printInfo("Loading driver: " + profile.driverPath);
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
