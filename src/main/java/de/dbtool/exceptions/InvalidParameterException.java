package de.dbtool.exceptions;

/**
 * Exception thrown when a parameter is invalid.
 */
public class InvalidParameterException extends Exception {
    public InvalidParameterException(String message) {
        super(message);
    }
}