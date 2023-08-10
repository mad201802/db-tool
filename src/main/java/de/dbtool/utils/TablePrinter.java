package de.dbtool.utils;

import com.github.freva.asciitable.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to print tables
 */
public class TablePrinter {
    private final int limitTextLength;

    /**
     * Creates a new instance of TablePrinter.
     *
     * @param limitTextLength The maximum length of a text in a cell. If the text is longer, it will be truncated.
     */
    public TablePrinter(int limitTextLength) {
        this.limitTextLength = limitTextLength;
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

        if (title != null) table += "\n\n => " + title + "\n";
        if (data.size() == 0) return "No data found";

        List<ColumnData<String[]>> columnData = new ArrayList<>();
        for (int i = 0; i < data.get(0).length; i++) {
            int finalI = i;

            Column column = new Column();
            if (this.limitTextLength > 0) column.maxWidth(this.limitTextLength, OverflowBehaviour.ELLIPSIS_RIGHT);
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
