package de.dbtool.drivers;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * This class is a wrapper for the JDBC Driver class
 */
public class DriverWrapper implements Driver {
    private final Driver driver;

    public DriverWrapper(Driver d) {
        this.driver = d;
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return this.driver.connect(url, info);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return this.driver.acceptsURL(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return this.driver.getPropertyInfo(url, info);
    }

    @Override
    public int getMajorVersion() {
        return this.driver.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
        return this.driver.getMinorVersion();
    }

    @Override
    public boolean jdbcCompliant() {
        return this.driver.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.driver.getParentLogger();
    }

    @Override
    public String toString() {
        return this.driver.toString();
    }
}
