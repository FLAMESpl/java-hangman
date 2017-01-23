package pl.wikihangman.web.infrastructure.page;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Constructs web page adding common content using {@code PrintWriter} set in
 * constructor.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class PageBuilder {

    private final PrintWriter out;
    private final List<String> body = new ArrayList<>();
    private String title = "";
    private String backToHome = "";
    
    /**
     * 
     * @param out {@code PrintWriter} to output page content
     */
    public PageBuilder(PrintWriter out) {
        this.out = out;
    }
    
    /**
     * Sets html header title.
     * 
     * @param title new title
     * @return this object
     */
    public PageBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    
    /**
     * Adds content to html body.
     * 
     * @param line new line body
     * @return this object
     */
    public PageBuilder insertText(String line) {
        body.add(line + "<br>");
        return this;
    }
    
    /**
     * Inserts form to html body.
     * 
     * @param formBuilder form-constructing fluent pipeline
     * @return this object
     */
    public PageBuilder insertForm(Consumer<Form> formBuilder) {
        Form form = new Form();
        formBuilder.accept(form);
        body.add(form.toString());
        return this;
    }
    
    /**
     * Inserts table to html body.
     * 
     * @param <T> type of table's model
     * @param tableBuilder table-constructing fluent pipeline
     * @return this object
     */
    public <T> PageBuilder insertTable(Consumer<Table<T>> tableBuilder) {
        Table<T> table = new Table<>();
        tableBuilder.accept(table);
        body.add(table.toString());
        return this;
    }
    
    /**
     * Includes html button allowing moving to home page.
     * 
     * @return this object
     */
    public PageBuilder includeBackToHomeButton() {
        backToHome = new Form()
            .setAction("home")
            .addInput(i -> i
                .setType("submit")
                .setValue("Back"))
            .toString();
        return this;
    }

    /**
     * Prints content stored in this object.
     */
    public void build() {
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println(title);
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");
        body.forEach(s -> out.println(s));
        out.println(backToHome);
        out.println("</body>");
        out.println("</html>");
    }
}
