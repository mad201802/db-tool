package de.dbtool.files.schemas;

import de.dbtool.cli.subcommands.options.SupportedDatabases;

public class Profile {

    public String name;
    public String hostname;
    public int port;
    public String dbName;
    public String username;
    public String password;
    public SupportedDatabases type;
    public String driverPath;

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
