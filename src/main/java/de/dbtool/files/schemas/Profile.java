package de.dbtool.files.schemas;

public class Profile {

    public String name;
    public String hostname;
    public String port;
    public String dbName;
    public String username;
    public String password;
    public String type;
    public String driverPath;

    public Profile(String name, String hostname, String port, String dbName, String username, String password, String type, String driverPath) {
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
