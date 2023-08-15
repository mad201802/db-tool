package de.dbtool.utils;

public class Utils {

    /**
     * Convert a pattern with * to a pattern with %
     * @param pattern The pattern to convert
     * @return The converted pattern
     */
    public static String toLikePattern(String pattern) {
        return pattern.replace("*", "%");
    }
}
