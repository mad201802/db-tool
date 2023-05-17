package de.dbtool.cli.subcommands.processors;

import de.dbtool.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class processes the column limit parameter.
 */
public class ColumnLimitProcessor {

    /**
     * This method processes the column limit parameter.
     * It takes a range string like "1,3,5-7" and returns an ArrayList with the numbers 1,3,5,6,7
     *
     * @param range The range string
     * @return An ArrayList with the sorted numbers
     * @throws InvalidParameterException If the range string is invalid
     */
    public static ArrayList<Integer> processColumnLimit(String range) throws InvalidParameterException {
        String[] queries = range.split(",");
        Set<Integer> querySet = new HashSet<>();

        try {
            for (String query : queries) {
                if (query.contains("-")) {
                    String[] rangeQuery = query.split("-");
                    int start = Integer.parseInt(rangeQuery[0]);
                    int end = Integer.parseInt(rangeQuery[1]);

                    for (int i = start; i <= end; i++) {
                        querySet.add(i);
                    }
                } else {
                    querySet.add(Integer.parseInt(query));
                }
            }
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Column limit must be a number");
        }

        if (querySet.contains(0)) throw new InvalidParameterException("Column limit cannot be 0");

        return new ArrayList<>(new TreeSet<>(querySet));
    }
}
