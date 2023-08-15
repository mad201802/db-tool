package de.dbtool.exceptions;

/**
 * This exception is thrown when an error occurs while using the DbTool
 */
public class DbToolException extends Exception {

    public DbToolException(String message) {
        super(message);
    }
}
