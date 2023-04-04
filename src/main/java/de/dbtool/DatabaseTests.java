package de.dbtool;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseTests {

    private Connection conn;

    public static void main(String[] args) throws SQLException {
        DatabaseTests dbTests = new DatabaseTests("jdbc:sqlite:src\\main\\resources\\test.sqlite");
//        DatabaseTests dbTests = new DatabaseTests("jdbc:mysql://192.168.178.60:6033/database", "admin", "admin");
        dbTests.printAllTables();
        System.out.println("====================================");
        dbTests.printAllTableColumns("tasks");
    }

    public DatabaseTests(String connectionString) throws SQLException {
         this.conn = DriverManager.getConnection(connectionString);
    }

    public DatabaseTests(String connectionString, String username, String password) throws SQLException {
        this.conn = DriverManager.getConnection(connectionString, username, password);
    }

    public void printAllTables() throws SQLException {
        //Retrieving the meta data object
        DatabaseMetaData metaData = this.conn.getMetaData();
        String[] types = {"TABLE"};
        //Retrieving the columns in the database
        ResultSet tables = metaData.getTables(null, null, "%", types);
        while (tables.next()) {
            System.out.println(tables.getString("TABLE_NAME"));
        }
    }

    public void printAllTableColumns(String tableName) throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        DatabaseMetaData metadata = this.conn.getMetaData();
        ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
        while (resultSet.next()) {
            String name = resultSet.getString("COLUMN_NAME");
            String type = resultSet.getString("TYPE_NAME");
            int size = resultSet.getInt("COLUMN_SIZE");

            System.out.println("Column name: [" + name + "]; type: [" + type + "]; size: [" + size + "]");
        }
    }



}
