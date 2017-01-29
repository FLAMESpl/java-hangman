package pl.wikihangman.web.services.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents SQL insert statement metadata.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class InsertStatement extends TableRelatedStatement {
    
    /**
     * 
     * @return SQL statement of table insertion
     */
    @Override
    public String toString() {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (KeyValuePair pair : data) {
            columns.add(pair.getKey());
            values.add(pair.getValue());
        }
        String query = new StringBuilder()
            .append("INSERT INTO ")
            .append(tableName)
            .append(" (")
            .append(String.join(",", columns))
            .append(")")
            .append(" VALUES (")
            .append(String.join(",", values))
            .append(")")
            .toString();
        return query;
    }
}
