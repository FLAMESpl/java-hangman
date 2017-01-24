package pl.wikihangman.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.wikihangman.web.infrastructure.page.PageBuilder;
import pl.wikihangman.web.models.User;
import pl.wikihangman.web.services.AccountsService;

/**
 * Handles request to display score of all players.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
@WebServlet(name = "ScoreboardServlet", urlPatterns = {"/score"})
public class ScoreboardServlet extends HttpServlet {
    
    /**
     * Displays table of all players' score with active player's score on top
     * of it if there is one.
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
            PageBuilder page = new PageBuilder(out);
            AccountsService accountsService = new AccountsService(getInitParameter("dbPath"));
            List<User> players = accountsService.getPlayersList();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("auth")) {
                        int id = Integer.parseInt(cookie.getValue());
                        User user = players.stream().filter(f -> f.getId() == id).findFirst().get();
                        page.insertText("Your score: " + Long.toString(user.getPoints()));
                    }
                }
            }
            page.<User>insertTable(t -> t
                        .setData(players)
                        .addColumn("Name", c -> c
                            .setModelBinder(x -> x.getName()))
                        .addColumn("Points", c -> c
                            .setModelBinder(x -> Long.toString(x.getPoints()))))
                    .includeBackButton("home")
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
