package pl.wikihangman.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.wikihangman.web.exceptions.EntityAlreadyExistsException;
import pl.wikihangman.web.infrastructure.AuthToken;
import pl.wikihangman.web.infrastructure.validation.Validator;
import pl.wikihangman.web.infrastructure.page.PageBuilder;
import pl.wikihangman.web.infrastructure.validation.ValidationResult;
import pl.wikihangman.web.models.User;
import pl.wikihangman.web.services.AccountsService;

/**
 * Handles requests to authenticate or create user.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
@WebServlet(name = "AccountsServlet", urlPatterns = {"/accounts"})
public class AccountsServlet extends HttpServlet {

    private AccountsService accountsService = null;
    private Validator validator = null;
    
    /**
     * Initializes required services and validators.
     */
    @Override
    public void init() {
       accountsService = new AccountsService(getInitParameter("dbPath"));
       validator = new Validator();
       validator.addRule(r -> r
                    .setPredicate(p -> p.getParameterMap().containsKey("user"))
                    .setErrorMessage(e -> "`user` parameter is required"))
                .addRule(r -> r
                    .setPredicate(p -> p.getParameterMap().containsKey("password"))
                    .setErrorMessage(e -> "`password` parameter is required"));
    }
    
    /**
     * Authenticates user of given credentials.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            PageBuilder page = new PageBuilder(out);
            ValidationResult validationResult = validator.test(request);
            
            if (!validationResult.isValid()) {
                validationResult.getErrors().forEach(e -> page.insertText(e));
                
            } else {
                String name = request.getParameter("user");
                String password = request.getParameter("password");

                User user = accountsService.authenticate(name, password);
                if (user != null) {
                    AuthToken token = new AuthToken(user);
                    response.addCookie(token);
                    page.insertText("Logged as " + user.getName());
                } else {
                    page.insertText("Invalid credentials");
                }
            }
            
            page.includeBackToHomeButton().build();
        }
    }

    /**
     * Creates new user of given credentials.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            PageBuilder page = new PageBuilder(out);
            ValidationResult validationResult = validator.test(request);
            
            if (!validationResult.isValid()) {
                validationResult.getErrors().forEach(e -> page.insertText(e));
                
            } else {
                String name = request.getParameter("user");
                String password = request.getParameter("password");

                try {
                    accountsService.register(name, password);
                    page.insertText("Successfully created " + name + " user");
                } catch (EntityAlreadyExistsException exception) {
                    page.insertText("User of " + name + " name already exists");
                }
            }
            page.includeBackToHomeButton().build();
        }
    }

}
