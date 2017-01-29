package pl.wikihangman.web.services.sql;

/**
 * Key-value pair of strings
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class KeyValuePair {

    private String key;
    private String value;
    
    /**
     * 
     * @param key string being a key
     * @param value string being a value
     */
    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    /**
     * 
     * @return pair's key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * 
     * @return pair's value
     */
    public String getValue() {
        return value;
    }
}
