package de.dbtool.utils;

public class Utils {

    public static String toLikePattern(String pattern) {
        return pattern.replace("*", "%");
    }
}
