package de.dbtool.drivers;

import de.dbtool.exceptions.DbToolException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.ServiceLoader;

public class JDBCDriverLoader {

    public static Driver loadDriver(String driverPath) throws DbToolException {
        File driverFile = new File(driverPath);
        if (!driverFile.exists()) {
            throw new DbToolException("Driver not found: " + driverPath);
        }
        try {
            URL[] urls = new URL[0];
            urls = new URL[]{driverFile.toURI().toURL()};

            URLClassLoader classLoader = new URLClassLoader(urls);
            ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class, classLoader);

            Driver driver = null;
            for (Driver d : serviceLoader) {
                driver = d;
            }

            if (driver == null) {
                throw new DbToolException("Driver not found: " + driverPath);
            }

            return new DriverWrapper(driver);

        } catch (MalformedURLException e) {
            throw new DbToolException("Error loading driver: " + driverPath);
        }
        }
}
