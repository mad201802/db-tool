package de.dbtool.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class contains methods to search for strings
 */
public class SearchUtils {

    /**
     * Get all strings in a list that match a regex
     * @param list The list to search in
     * @param regex The regex to search for
     * @return A list of all strings that match the regex
     */
    public static List<String> getMatchingStrings(List<String> list, String regex) {

        ArrayList<String> matches = new ArrayList<String>();

        Pattern p = Pattern.compile(regex);

        for (String s:list) {
            if (p.matcher(s).matches()) {
                matches.add(s);
            }
        }

        return matches;
    }
}
