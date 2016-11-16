package pl.wikihangman.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * {@code DependencyContainer} allows obtaining object instances by theirs type
 * with already initialized required parameters.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class DependencyContainer {
    
    private Map<Class, Callable<Object>> factories = new HashMap<>();
    
    /**
     * Adds object factory to the map.
     * 
     * @param <T> Class type
     * @param typeInfo Class type variable
     * @param factory Function returning new instance of given class
     */
    public <T> void add(Class<T> typeInfo, Callable<Object> factory) {
        factories.put(typeInfo, factory);
    }
    
    /**
     * Returns object factory for given type.
     * 
     * @param <T> Class type
     * @param typeInfo Class type variable
     * @return Function returning new instance of given class
     */
    public <T> Callable<Object> get(Class<T> typeInfo) {
        return factories.get(typeInfo);
    }
    
    /**
     * Returns new instance of given type.
     * 
     * @param <T> Class type
     * @param typeInfo Class type info
     * @return New object of given type.
     * @throws Exception 
     */
    public <T> T getInstance(Class<T> typeInfo) throws Exception {
        return (T)factories.get(typeInfo).call();
    }
}
