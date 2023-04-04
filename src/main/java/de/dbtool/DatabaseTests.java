package de.dbtool;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseTests {

    private Connection conn;

    public static void main(String[] args) throws SQLException {
        DatabaseTests dbTests = new DatabaseTests("jdbc:sqlite:test.sqlite");
        System.out.println(dbTests.getTables());
    }

    public DatabaseTests(String connectionString) throws SQLException {
         this.conn = DriverManager.getConnection(connectionString);
    }

    public ArrayList<String> getTables() throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        //Retrieving the meta data object
        DatabaseMetaData metaData = this.conn.getMetaData();
        String[] types = {"TABLE"};
        //Retrieving the columns in the database
        ResultSet tables = metaData.getTables(null, null, "%", types);
        while (tables.next()) {
            result.add(tables.getString("TABLE_NAME"));
        }

        return result;
    }



}
