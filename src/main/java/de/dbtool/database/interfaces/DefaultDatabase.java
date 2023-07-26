package de.dbtool.database.interfaces;

import de.dbtool.cli.subcommands.options.SupportedDatabases;
import de.dbtool.drivers.JDBCDriverLoader;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.ASCIIArt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        return null;
    }

    @Override
    public List<String> getColumnValues(String tableName, String columnName) throws DbToolException {
        return null;
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
