package pl.wikihangman.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.client.ClientProtocolException;
import pl.wikihangman.web.exceptions.EntityDoesNotExistException;
import pl.wikihangman.web.infrastructure.AuthToken;
import pl.wikihangman.web.infrastructure.page.PageBuilder;
import pl.wikihangman.web.infrastructure.validation.ValidationResult;
import pl.wikihangman.web.infrastructure.validation.Validator;
import pl.wikihangman.web.models.Hangman;
import pl.wikihangman.web.models.Letter;
import pl.wikihangman.web.models.User;
import pl.wikihangman.web.services.AccountsService;
import pl.wikihangman.web.services.WikipediaService;

/**
 * Handles requests to create new hangman and discover its letters.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
@WebServlet(name = "HangmanServlet", urlPatterns = {"/hangman"})
public class HangmanServlet extends HttpServlet {

    private final static Character[] AVAILABLE_CHARS = {
        'A', 'Ą', 'B', 'C', 'Ć', 'D', 'E', 'Ę', 'F', 'G', 'H', 'I', 'J', 'K', 
        'L', 'Ł', 'M', 'N', 'Ń', 'O', 'Ó', 'P', 'Q', 'R', 'S', 'Ś', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z', 'Ź', 'Ż'
    };
    
    private WikipediaService wikiService = null;
    private AccountsService accountsService = null;
    private Validator getValidator = null;
    private Validator postValidator = null;
    
    /**
     * Initializes necessary services and validators.
     */
    @Override
    public void init() {
        accountsService = new AccountsService(getInitParameter("dbPath"));
        
        wikiService = new WikipediaService()
            .setAvaliableChars(new HashSet<>(Arrays.asList(AVAILABLE_CHARS)))
            .setWikiHost(getInitParameter("wikiHost"))
            .setMaxLength(Integer.parseInt(getInitParameter("maxLength")))
            .setMaxRetries(Integer.parseInt(getInitParameter("maxRetries")))
            .setTimeout(Integer.parseInt(getInitParameter("timeout")))
            .setQuerySize(Integer.parseInt(getInitParameter("querySize")));
        
        getValidator = new Validator()
            .addRule(r -> r
                .setPredicate(p -> p.getParameterMap().containsKey("lives"))
                .setErrorMessage(e -> "`lives` parameter is required")
                .addSubsequent(sr -> sr
                    .setPredicate(p -> isInteger(p.getParameter("lives")))
                    .setErrorMessage(e -> String.format(
                        "Parameter `lives` must be integer but is `%1$s`",
                        e.getParameter("lives")))));
        
        postValidator = new Validator()
            .addRule(r -> r
                .setPredicate(p -> p.getParameterMap().containsKey("character"))
                .setErrorMessage(e -> "`character` parameter is required")
                .addSubsequent(sr -> sr
                    .setPredicate(p -> p.getParameter("character").length() == 1)
                    .setErrorMessage(e -> String.format(
                        "Paramter `character` must be one character long but is `%1$s`",
                        e.getParameter("character")))
                    .addSubsequent(ssr -> ssr
                        .setPredicate(p -> isAllowed(p.getParameter("character").charAt(0)))
                        .setErrorMessage(e -> String.format(
                            "Value of paramter `character` (%1$s) is not allowed",
                            e.getParameter("character"))))));
    }
    
    /**
     * Checks if value is integer
     * 
     * @param str string to be parsed
     * @return true if str is integer, otherwise false
     */
    private boolean isInteger(String str) {
        boolean result = true;
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            result = false;
        }
        return result;
    }
    
    /**
     * Tests if given character is allowed in hangman
     * 
     * @param ch character to test
     * @return true if is allowed, otherwise false
     */
    private boolean isAllowed(char ch) {
        int i;
        for (i = 0; i < AVAILABLE_CHARS.length; i++) {
            if (AVAILABLE_CHARS[i] == Character.toUpperCase(ch))
                break;
        }
        return AVAILABLE_CHARS.length > i;
    }
                        
    /**
     * Creates common page content for both requests.
     * 
     * @param page page builder
     * @param hangman model of current hangman
     * @param usedLetters letters already used in this hangman
     */
    private void createPageContent(PageBuilder page, Hangman hangman, Map<Character, Boolean> usedLetters) {
        
        String[] keyword = new String[hangman.getKeywordsLength()];
        for (int i = 0; i < keyword.length; i++) {
            Letter letter = hangman.getKeyword().get(i);
            keyword[i] = Character.toString(letter.isDiscovered() ? letter.getCharacter() : '_');
        }
        
        page.insertText(hangman.getArticleCategory())
            .insertText(String.format("Lives: %1$d/%2$d", hangman.getActualLives(), hangman.getMaxLives()))
            .insertText(String.join(" ", keyword))
            .insertForm(f -> f.setAction("hangman").setMethod("post")
                .addInput(i -> i.setType("text").setName("character"))
                .addInput(i -> i.setType("submit").setValue("Discover")))
            .<Map.Entry<Character, Boolean>>insertTable(t -> t
                .setData(usedLetters.entrySet())
                .sortOn("Letter")
                .addColumn("Letter", c -> c.setModelBinder(x -> x.getKey().toString()))
                .addColumn("Succeded", c -> c.setModelBinder(x -> x.getValue().toString())));
    }
    
    /**
     * Handles requests to create new hangman that is saved in http session.
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
            ValidationResult validationResult = getValidator.test(request);
            
            if (!validationResult.isValid()) {
                validationResult.getErrors().forEach(e -> page.insertText(e));
                
            } else {
                AuthToken token = AuthToken.getFrom(request.getCookies());

                if (token == null) {
                    page.insertText("Log in first to play a game");
                } else {

                    try {
                        Hangman hangman = wikiService.createHangman(10);
                        HttpSession session = request.getSession();
                        Map<Character, Boolean> usedLetters = new HashMap<>();
                        session.setAttribute("hangman", hangman);
                        session.setAttribute("letters", usedLetters);
                        createPageContent(page, hangman, usedLetters);

                    } catch (URISyntaxException | ClientProtocolException ex) {
                        page.insertText("Could not create valid request to wikipedia");

                    } catch (TimeoutException | InterruptedException ex) {
                        page.insertText("Could not get hangman at this moment, try again")
                            .insertForm(f -> f.setAction("hangman").setMethod("get")
                                .addInput(i -> i.setType("submit").setValue("Try again")));
                    }
                }
            }
            
            page.includeBackToHomeButton().build();
        }
    }

    /**
     * Handles requests to discover letter in hangman.
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
            ValidationResult validationResult = postValidator.test(request);
            
            if (!validationResult.isValid()) {
                validationResult.getErrors().forEach(e -> page.insertText(e));
                
            } else {
                AuthToken token = AuthToken.getFrom(request.getCookies());

                if (token == null) {
                    page.insertText("Log in first to play a game");
                } else {
                    Character character = request.getParameter("character").charAt(0);
                    HttpSession session = request.getSession();
                    Hangman hangman = (Hangman)session.getAttribute("hangman");
                    Map<Character, Boolean> usedLetters = (Map<Character, Boolean>)session.getAttribute("letters");

                    int result = hangman.discover(character);
                    usedLetters.put(character, result > 0);

                    createPageContent(page, hangman, usedLetters);
                    if (hangman.isGameOver()) {
                        page.insertText("Game is over");
                        if (hangman.hasAnyLivesLeft()) {
                            page.insertText("You won");
                            List<User> users = accountsService.getPlayersList();
                            int id = Integer.parseInt(token.getValue());
                            User user = users.stream().filter(u -> u.getId() == id).findFirst().get();
                            user.score(hangman.getMaxLives());
                            try {
                                accountsService.update(user);
                            } catch (EntityDoesNotExistException ex) {
                                page.insertText("Failed to update player status");
                            }
                        } else {
                            page.insertText("You lost");
                        }
                    }

                    session.setAttribute("hangman", hangman);
                    session.setAttribute("letters", usedLetters);
                }
            }
            
            
            page.includeBackToHomeButton().build();
        }
    }

}
