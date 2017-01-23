package pl.wikihangman.web.infrastructure.page;

/**
 * Represents attribute in html marker.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Attribute {
    
    private final String name;
    private String value;

    /**
     * 
     * @param name name of this attribute
     */
    public Attribute(String name) {
        this.name = name;
    }

    /**
     * @param value the value to set
     */
    public void set(String value) {
        this.value = value;
    }
    
    /**
     * Tests if attribute has value. By default, it has not.
     * 
     * @return true if this attribute has value, otherwise false
     */
    public boolean hasValue() {
        return value != null;
    }
    
    /**
     * 
     * @return fragment of html code being representation of this attribute
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.append(" ")
                      .append(name)
                      .append("=\"")
                      .append(value)
                      .append("\"")
                      .toString();
    }
}
