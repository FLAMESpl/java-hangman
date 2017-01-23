package pl.wikihangman.web.infrastructure.page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents html form with it attributes.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Form {

    private Attribute action = new Attribute("action");
    private Attribute method = new Attribute("method");
    private final List<Input> inputs = new ArrayList<>();
    
    /**
     * 
     * @param action action for this form
     * @return this object
     */
    public Form setAction(String action) {
        this.action.set(action);
        return this;
    }
    
    /**
     * 
     * @param method method for this form
     * @return this object
     */
    public Form setMethod(String method) {
        this.method.set(method);
        return this;
    }
    
    /**
     * Adds input to the form.
     * 
     * @param inputBuilder fluent pipeline to build input
     * @return this value
     */
    public Form addInput(Consumer<Input> inputBuilder) {
        Input input = new Input();
        inputBuilder.accept(input);
        inputs.add(input);
        return this;
    }
    
    /**
     * 
     * @return html representation of this form
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<form");
        if (action.hasValue())
            builder.append(action.toString());
        if (method.hasValue())
            builder.append(method.toString());
        builder.append(">");
        inputs.forEach(i -> builder.append(i.toString()));
        builder.append("</form>");
        return builder.toString();
    }
}
