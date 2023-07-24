package de.dbtool.database;

import de.dbtool.cli.subcommands.options.SupportedDatabases;
import de.dbtool.drivers.JDBCDriverLoader;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.ASCIIArt;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class QueryProcessor {
    private final Profile profile;
    private String databaseType;
    private Query query;
    private Connection connection;

    public QueryProcessor(Profile profile, Query query) throws DbToolException {
        this.profile = profile;
        this.query = query;
        this.loadDriverIfNecessary();
        this.connect();
    }

    public QueryProcessor(Profile profile) throws DbToolException {
        this(profile, null);
    }

    public List<String[]> executeQuery() {
        if (query == null) throw new RuntimeException("Query is null");
        // TODO: Implement query execution

        return null;
    }

    public void connect() throws DbToolException {
        System.out.println("Connecting to database: " + profile.hostname + ":" + profile.port + "/" + profile.dbName);
        Properties connectionProps = new Properties();
        connectionProps.put("user", profile.username);
        connectionProps.put("password", profile.password);

        String url = "jdbc:" + this.databaseType + "://" + profile.hostname + ":" + profile.port + "/" + profile.dbName;
        try {
            connection = DriverManager.getConnection(url, connectionProps);
            System.out.println("Successfully connected to database!");
        } catch (SQLException e) {
            throw new DbToolException("Error connecting to database: " + e.getMessage());
        }
    }

    public void setQuery(Query query) {
        this.query = query;
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
