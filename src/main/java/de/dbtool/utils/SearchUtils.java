package de.dbtool.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchUtils {
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
