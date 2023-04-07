package de.dbtool.drivers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ServiceLoader;

public class JDBCDriverLoader {

        public static Driver loadDriver(String driverPath) throws IOException, SQLException {
            File driverFile = new File(driverPath);
            if(!driverFile.exists()) {
                throw new RuntimeException("Driver file not found: " + driverPath);
            }
            URL[] urls = { driverFile.toURI().toURL() };
            URLClassLoader classLoader = new URLClassLoader(urls);
            ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class, classLoader);

            Driver driver = null;
            for (Driver d : serviceLoader) {
                driver = d;
            }

            if(driver == null) {
                throw new RuntimeException("Driver not found: " + driverPath);
            }

            return new DriverWrapper(driver);
        }
}
