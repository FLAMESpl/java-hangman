package pl.wikihangman.web.services.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents SQL select statement metadata.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class SelectStatement extends TableRelatedStatement {

    /**
     * 
     * @return SQL statement of table selection
     */
    @Override
    public String toString() {
        List<String> constraints = new ArrayList<>();
        data.forEach(d -> constraints.add(String.format("%1$s=%2$s", d.getKey(), d.getValue())));
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ").append(tableName);
        if (!constraints.isEmpty()) {
            builder.append(" WHERE ").append(String.join(" AND ", constraints));
        }
        String query = builder.toString();
        return query;
    }
}
