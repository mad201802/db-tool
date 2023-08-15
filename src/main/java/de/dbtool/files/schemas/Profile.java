package de.dbtool.files.schemas;

import de.dbtool.cli.subcommands.options.SupportedDatabases;

public class Profile {

    public final String name;
    public final String hostname;
    public final int port;
    public final String dbName;
    public final String username;
    public final String password;
    public final SupportedDatabases type;
    public final String driverPath;

    public Profile(String name, String hostname, int port, String dbName, String username, String password, SupportedDatabases type, String driverPath) {
        this.name = name;
        this.hostname = hostname;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.type = type;
        this.driverPath = driverPath;
    }

    @Override
    public String toString() {
        return "[Profile] " + name + " | " + hostname + ":" + port + " | " + dbName + " | " + username + " | " + password + " | " + type + " | " + driverPath + "\n";
    }
}
