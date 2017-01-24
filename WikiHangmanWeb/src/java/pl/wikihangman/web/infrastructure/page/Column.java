package pl.wikihangman.web.infrastructure.page;

import java.util.function.Function;

/**
 * Represents column in html table for given model type.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 * @param <T> type of table's model
 */
public class Column<T> {

    private Function<T, String> modelBinder = t -> t.toString();
    private String name = "";
    
    /**
     * Creates new column with given name. Names must be unique in one table.
     * 
     * @param name name of this column
     */
    public Column(String name) {
        this.name = name;
    }
    
    /**
     * Model binder tells how this column represents current record. By default
     * it is {@code toString} method.
     * 
     * @param modelBinder new modelBinder value
     * @return this object
     */
    public Column<T> setModelBinder(Function<T, String> modelBinder) {
        this.modelBinder = modelBinder;
        return this;
    }
    
    /**
     * Returns cell value for model returned by column's model binder.
     * 
     * @param model model that value is taken from
     * @return value for given model
     */
    public String value(T model) {
        return modelBinder.apply(model);
    }
    
}
