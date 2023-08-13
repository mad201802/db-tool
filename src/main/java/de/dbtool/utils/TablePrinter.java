package de.dbtool.utils;

import com.github.freva.asciitable.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class to print tables
 */
public class TablePrinter {
    private final int defaultLimitTextLength;

    /**
     * Creates a new instance of TablePrinter.
     *
     * @param defaultLimitTextLength The maximum length of a text in a cell. If the text is longer, it will be truncated.
     */
    public TablePrinter(int defaultLimitTextLength) {
        this.defaultLimitTextLength = defaultLimitTextLength;
    }

    /**
     * Creates ascii table from the title and provided data.
     *
     * @param title The title of the table
     * @param data  The data to be displayed in the table
     * @param tempLimitTextLength OPTIONAL: The maximum length of a text in a cell. If the text is longer, it will be truncated. If not defined, the default value that was passed to the constructor will be used.
     * @return The ascii table as a string
     */
    public String getTableString(String title, List<String[]> data, Optional<Integer> tempLimitTextLength) {
        String table = "";
        int limitTextLength = tempLimitTextLength.isPresent() ? tempLimitTextLength.get() : this.defaultLimitTextLength;

        if (title != null) table += "\n\n => " + title + "\n";
        if (data.size() == 0) return "No data found";

        List<ColumnData<String[]>> columnData = new ArrayList<>();
        for (int i = 0; i < data.get(0).length; i++) {
            int finalI = i;

            Column column = new Column();
            if (limitTextLength > 0) column.maxWidth(this.defaultLimitTextLength, OverflowBehaviour.ELLIPSIS_RIGHT);
            column.dataAlign(HorizontalAlign.CENTER);
            columnData.add(column.with(c -> c[finalI]));
        }

        table += AsciiTable.getTable(data, columnData);

        return table;
    }

    public String censorPassword(String password) {
        if(password == null) return null;
        return password.replaceAll(".", "*");
    }
}
