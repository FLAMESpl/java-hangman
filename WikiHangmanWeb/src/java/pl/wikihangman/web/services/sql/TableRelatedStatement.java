package pl.wikihangman.web.services.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents abstraction of SQL statement executed on a table.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public abstract class TableRelatedStatement {

    protected String tableName;
    protected final List<KeyValuePair> data = new ArrayList<>();
    
    /**
     * 
     * @param tableName name of table that data is inserted into
     * @return this object
     */
    public TableRelatedStatement setTable(String tableName) {
        this.tableName = tableName;
        return this;
    }
    
    /**
     * Adds data to insert statement
     * 
     * @param columnName name of column that data is inserted into
     * @param value value of inserted data
     * @return this object
     */
    public TableRelatedStatement addData(String columnName, String value) {
        data.add(new KeyValuePair(columnName, value));
        return this;
    }
}
