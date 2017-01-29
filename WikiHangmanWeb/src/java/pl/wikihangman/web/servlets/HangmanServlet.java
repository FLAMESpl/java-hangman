package pl.wikihangman.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
import pl.wikihangman.web.views.UsedLettersHashMap;
import pl.wikihangman.web.views.UsedLettersMap;

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
    
    private final String parameterLives = "lives";
    private final String parameterCharacter = "character";
    private final String attributeHangman = "hangman";
    private final String attributeLetters = "letters";
    private final String attributeScored = "scored";
    
    private WikipediaService wikiService = null;
    private AccountsService accountsService = null;
    private Validator getValidator = null;
    private Validator postValidator = null;
    
    /**
     * Initializes necessary services and validators.
     */
    @Override
    public void init() {
        try {
            accountsService = AccountsService.getInstance(getInitParameter("dbPath"));
        } catch (ClassNotFoundException | SQLException ex) {
           System.err.println(ex.getMessage());
        }
        
        wikiService = new WikipediaService()
            .setAvaliableChars(new HashSet<>(Arrays.asList(AVAILABLE_CHARS)))
            .setWikiHost(getInitParameter("wikiHost"))
            .setMaxLength(Integer.parseInt(getInitParameter("maxLength")))
            .setMaxRetries(Integer.parseInt(getInitParameter("maxRetries")))
            .setTimeout(Integer.parseInt(getInitParameter("timeout")))
            .setQuerySize(Integer.parseInt(getInitParameter("querySize")));
        
        getValidator = new Validator()
            .addRule(r -> r
                .addCondition(c -> !c.getParameterMap().containsKey(parameterLives))
                .setPredicate(p -> {
                    HttpSession session = p.getSession();
                    return session.getAttribute(attributeHangman) != null &&
                           session.getAttribute(attributeLetters) != null;
                })
                .setErrorMessage(p -> "There is no active hangman session"))
            .addRule(r -> r
                .addCondition(c -> c.getParameterMap().containsKey(parameterLives))
                .setPredicate(p -> isInteger(p.getParameter(parameterLives)))
                .setErrorMessage(e -> String.format(
                    "Parameter `%2$s` must be integer but is `%1$s`",
                    e.getParameter(parameterLives), parameterLives))
                .addSubsequentRule(sr -> sr
                    .setPredicate(p -> isAllowedLivesAmount(Integer.parseInt(p.getParameter(parameterLives))))
                    .setErrorMessage(e -> String.format(
                        "Paramter `%2$s` must be a number between 0 and 10 but is `%1$s`",
                        e.getParameter(parameterLives), parameterLives))));
        
        postValidator = new Validator()
            .addRule(r -> r
                .setPredicate(p -> {
                    HttpSession session = p.getSession();
                    return session.getAttribute(attributeHangman) != null &&
                           session.getAttribute(attributeLetters) != null &&
                           session.getAttribute(attributeScored) != null;
                })
                .setErrorMessage(p -> "There is no active hangman session"))
            .addRule(r -> r
                .setPredicate(p -> p.getParameterMap().containsKey(parameterCharacter))
                .setErrorMessage(e -> String.format(
                        "`%1$s` parameter is required",
                        parameterCharacter))
                .addSubsequentRule(sr -> sr
                    .setPredicate(p -> p.getParameter(parameterCharacter).length() == 1)
                    .setErrorMessage(e -> String.format(
                        "Paramter `%2$s` must be one character long but is `%1$s`",
                        e.getParameter(parameterCharacter), parameterCharacter))
                    .addSubsequentRule(ssr -> ssr
                        .setPredicate(p -> isAllowed(p.getParameter(parameterCharacter).charAt(0)))
                        .setErrorMessage(e -> String.format(
                            "Value `%1$s`of paramter `%2$s` is not allowed",
                            e.getParameter(parameterCharacter), parameterCharacter)))));
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
     * Tests number if it is valid amount of lives for the hangman.
     * 
     * @param number number to test
     * @return true if is valid, otherwise false
     */
    private boolean isAllowedLivesAmount(int number) {
        return number > 0 && number <= 10;
    }
                        
    /**
     * Creates common page content for both requests.
     * 
     * @param page page builder
     * @param hangman model of current hangman
     * @param usedLetters letters already used in this hangman
     */
    private void createPageContent(PageBuilder page, Hangman hangman, UsedLettersMap usedLetters) {
        
        if (hangman.isGameOver()) {
            page.insertText("Game is over");
            page.insertText(hangman.hasAnyLivesLeft() ? "You won" : "You lost");
        }
        
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
        
        if (hangman.isGameOver()) {
            page.insertText(hangman.getArticleInformation());
        }
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
                        Hangman hangman = null;
                        UsedLettersMap usedLetters = null;
                            HttpSession session = request.getSession();
                        
                        if (request.getParameterMap().containsKey(parameterLives)) {
                            int lives = Integer.parseInt(request.getParameter(parameterLives));
                            hangman = wikiService.createHangman(lives);
                            usedLetters = new UsedLettersHashMap();
                            session.setAttribute(attributeHangman, hangman);
                            session.setAttribute(attributeLetters, usedLetters);
                            session.setAttribute(attributeScored, false);
                        } else {
                            hangman = (Hangman)session.getAttribute(attributeHangman);
                            usedLetters = (UsedLettersMap)session.getAttribute(attributeLetters);
                        }
                        
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
            
            page.includeBackButton("home").build();
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
            String backButtonPath;
            
            if (!validationResult.isValid()) {
                validationResult.getErrors().forEach(e -> page.insertText(e));
                backButtonPath = "hangman";
                
            } else {
                AuthToken token = AuthToken.getFrom(request.getCookies());
                backButtonPath = "home";

                if (token == null) {
                    page.insertText("Log in first to play a game");
                } else {
                    Character character = request.getParameter(parameterCharacter).charAt(0);
                    HttpSession session = request.getSession();
                    Hangman hangman = (Hangman)session.getAttribute(attributeHangman);
                    UsedLettersMap usedLetters = (UsedLettersMap)session.getAttribute(attributeLetters);
                    
                    if (!hangman.isGameOver()) {
                        int result = hangman.discover(character);
                        usedLetters.put(character, result > 0);
                    }
                    
                    if (hangman.isGameOver() && hangman.hasAnyLivesLeft() && 
                        (boolean)session.getAttribute(attributeScored)) {
                        
                        try {
                            List<User> users = accountsService.getPlayersList();
                            int id = Integer.parseInt(token.getValue());
                            User user = users.stream().filter(u -> u.getId() == id).findFirst().get();
                            user.score(hangman.getMaxLives());
                            accountsService.update(user);
                            session.setAttribute(attributeScored, true);
                        } catch (EntityDoesNotExistException | SQLException | NullPointerException ex) {
                            page.insertText("Failed to update player status");
                        }
                    }
                    
                    createPageContent(page, hangman, usedLetters);
                    session.setAttribute(attributeHangman, hangman);
                    session.setAttribute(attributeLetters, usedLetters);
                }
            }
            
            page.includeBackButton(backButtonPath).build();
        }
    }

}
