package pl.wikihangman.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.wikihangman.web.infrastructure.page.PageBuilder;

/**
 * Displays main page from where user can navigate.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    /**
     * Displays home page.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            PageBuilder page = new PageBuilder(out);
            page.insertForm(f -> f.setAction("accounts").setMethod("get")
                    .addInput(i -> i.setType("text").setName("user"))
                    .addInput(i -> i.setType("text").setName("password"))
                    .addInput(i -> i.setType("submit").setValue("Log in")))
                .insertForm(f -> f.setAction("accounts").setMethod("post")
                    .addInput(i -> i.setType("text").setName("user"))
                    .addInput(i -> i.setType("text").setName("password"))
                    .addInput(i -> i.setType("submit").setValue("Sign up")))
                .insertForm(f -> f.setAction("logout")
                    .addInput(i -> i.setType("submit").setValue("Log out")))
                .insertForm(f -> f.setAction("score")
                    .addInput(i -> i.setType("submit").setValue("Scoreboard")))
                .insertForm(f -> f.setAction("hangman").setMethod("get")
                    .addInput(i -> i.setType("text").setName("lives").setValue("10"))
                    .addInput(i -> i.setType("submit").setValue("Start game")))
                .insertForm(f -> f.setAction("hangman").setMethod("get")
                    .addInput(i -> i.setType("submit").setValue("Resume game")))
                .build();
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
