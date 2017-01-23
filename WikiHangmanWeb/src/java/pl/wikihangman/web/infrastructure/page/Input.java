package pl.wikihangman.web.infrastructure.page;

/**
 * Represents html input and its attributes.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Input {

    private final Attribute type = new Attribute("type");
    private final Attribute name = new Attribute("name");
    private final Attribute value = new Attribute("value");
    
    /**
     * 
     * @param type the type to set
     * @return this object
     */
    public Input setType(String type) {
        this.type.set(type);
        return this;
    }

    /**
     * @param name the name to set
     * @return this object
     */
    public Input setName(String name) {
        this.name.set(name);
        return this;
    }

    /**
     * @param value the value to set
     * @return this object
     */
    public Input setValue(String value) {
        this.value.set(value);
        return this;
    }
    
    /**
     * 
     * @return this object as html code
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<input");
        if (type.hasValue())
            builder.append(type.toString());
        if (name.hasValue())
            builder.append(name.toString());
        if (value.hasValue())
            builder.append(value.toString());
        builder.append(" />");
        return builder.toString();
    }
}
