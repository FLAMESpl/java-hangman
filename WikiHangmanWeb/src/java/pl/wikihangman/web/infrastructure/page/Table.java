package pl.wikihangman.web.infrastructure.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents html table with collection of columns.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 * @param <T> type of table's model
 */
public class Table<T> {

    private final Map<String, Column<T>> columns = new HashMap<>();
    private List<T> rows = new ArrayList<>();
    private String sortColumn;
    
    /**
     * Adds column to the table. Name must be unique for columns in one table.
     * 
     * @param name column's name
     * @param columnBuilder fluent pipeline to build column
     * @return 
     */
    public Table<T> addColumn(String name, Consumer<Column<T>> columnBuilder) {
        Column column = new Column(name);
        columnBuilder.accept(column);
        columns.put(name, column);
        return this;
    }
    
    /**
     * Adds model that is representation of an row in the table.
     * 
     * @param model data to add
     * @return this object
     */
    public Table<T> addRow(T model) {
        rows.add(model);
        return this;
    }
    
    /**
     * Seeds rows with from the list. If {@code data} is null, does nothing.
     * 
     * @param data model for this table
     * @return this object
     */
    public Table<T> setData(Collection<T> data) {
        if (data != null)
            rows = new ArrayList<>(data);
        return this;
    }
    
    /**
     * Sets on which column table should be sorted.
     * 
     * @param columnName name of the column
     * @return this object
     */
    public Table<T> sortOn(String columnName) {
        sortColumn = columnName;
        return this;
    }
    
    /**
     * 
     * @return html representation of this table
     */
    @Override
    public String toString() {
        if (sortColumn != null && columns.containsKey(sortColumn)) {
            Column column = columns.get(sortColumn);
            rows.sort((o1, o2) -> column.value(o1).compareToIgnoreCase(column.value(o2)));
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<table>");
        for (T row : rows) {
            builder.append("<tr>");
            columns.values().forEach(c -> builder.append("<td>")
                                                 .append(c.value(row))
                                                 .append("</td>"));
            builder.append("</tr>");
        }
        builder.append("</table>");
        return builder.toString();
    }
}
