package de.dbtool.database.interfaces;

import de.dbtool.exceptions.DbToolException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDatabase {
    void connect() throws DbToolException;

    List<String> getAllDatabaseTables() throws DbToolException;

    List<String> getDatabaseTables(String pattern) throws DbToolException;

    List<String> getTableColumns(String tableName) throws DbToolException;

    List<String> getTableColumns(String tableName, String pattern) throws DbToolException;

    List<List<String>> getValues(String table, Set<String> columns, List<String> patterns, List<String> compares, boolean useAnd, Optional<Integer> limit) throws DbToolException ;
}
