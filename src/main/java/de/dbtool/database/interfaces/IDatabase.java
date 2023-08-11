package de.dbtool.database.interfaces;

import de.dbtool.exceptions.DbToolException;

import java.util.List;

public interface IDatabase {
    void connect() throws DbToolException;

    List<String> getAllDatabaseTables() throws DbToolException;

    List<String> getDatabaseTables(String pattern) throws DbToolException;

    List<String> getTableColumns(String tableName) throws DbToolException;

    List<String> getTableColumns(String tableName, String pattern) throws DbToolException;

    List<String> getColumnValues(String tableName, String columnName) throws DbToolException;
}
