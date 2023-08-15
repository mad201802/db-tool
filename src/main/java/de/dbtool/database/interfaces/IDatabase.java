package de.dbtool.database.interfaces;

import de.dbtool.exceptions.DbToolException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDatabase {

    /**
     * Connect to the database
     * @throws DbToolException If an error occurs while connecting
     */
    void connect() throws DbToolException;

    /**
     * Get all table names from the database
     * @return A list of table names
     * @throws DbToolException If an error occurs while getting the tables
     */
    List<String> getAllDatabaseTables() throws DbToolException;

    /**
     * Get all table names from the database that match the given pattern
     * @param pattern The pattern to match (e.g. "users" or "user%")
     * @return A list of table names
     * @throws DbToolException If an error occurs while getting the tables
     */
    List<String> getDatabaseTables(String pattern) throws DbToolException;

    /**
     * Get all columns from a table
     * @param tableName The name of the table
     * @return A list of column names
     * @throws DbToolException If an error occurs while getting the columns
     */
    List<String> getTableColumns(String tableName) throws DbToolException;

    /**
     * Get all columns from a table that match the given pattern
     * @param tableName The name of the table
     * @param pattern The pattern to match (e.g. "users" or "user%")
     * @return A list of column names
     * @throws DbToolException If an error occurs while getting the columns
     */
    List<String> getTableColumns(String tableName, String pattern) throws DbToolException;

    /**
     * Find all rows in a specific table that match the given patterns
     * @param table The name of the table
     * @param columns The columns to search in
     * @param patterns The string patterns to match values against
     * @param compares The compare clauses to check against
     * @param useAnd Whether to use AND or OR when combining the patterns and compares
     * @param limit Limit the number of rows to return
     * @return A list of rows that match the given patterns
     * @throws DbToolException If an error occurs while getting the rows, for example if a compare clauses uses invalid column names
     */
    List<List<String>> getValues(String table, Set<String> columns, List<String> patterns, List<String> compares, boolean useAnd, Integer limit) throws DbToolException ;
}
