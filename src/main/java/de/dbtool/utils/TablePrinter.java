package de.dbtool.utils;

import com.github.freva.asciitable.*;

import java.util.ArrayList;
import java.util.List;

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
     * @return The ascii table as a string
     */
    public String getTableString(String title, List<String[]> data) {
        String table = "";

        if (title != null) table += "\033[0;32m" + "\n\n => " + title + "\n" + "\033[0m";
        if (data.size() == 0) return "No data found";

        List<ColumnData<String[]>> columnData = new ArrayList<>();
        for (int i = 0; i < data.get(0).length; i++) {
            int finalI = i;

            Column column = new Column();
            if (this.defaultLimitTextLength > 0) column.maxWidth(this.defaultLimitTextLength, OverflowBehaviour.ELLIPSIS_RIGHT);
            column.dataAlign(HorizontalAlign.CENTER);
            columnData.add(column.with(c -> c[finalI]));
        }
        Character[] borderStyles = AsciiTable.FANCY_ASCII;
        table += AsciiTable.getTable(borderStyles, data, columnData);

        return table;
    }

    public String censorPassword(String password) {
        if(password == null) return null;
        return password.replaceAll(".", "*");
    }
}
