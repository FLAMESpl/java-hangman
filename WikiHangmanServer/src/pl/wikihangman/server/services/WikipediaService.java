package pl.wikihangman.server.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.wikihangman.server.models.Hangman;

/**
 * Manages requests to wikipedia api for hangman keywords.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class WikipediaService {

    private static final int REQUEST_TIMEOUT = 1000;
    
    private int querySize = 10;
    private int timeout = 1000;
    private int maxRetries = 5;
    private int maxLength = 15;
    private String wikiHost;
    private Set<Character> availableChars;
    
    /**
     * 
     * @param availableChars set of available characters in keywords
     * @return this object
     */
    public WikipediaService setAvaliableChars(Set<Character> availableChars) {
        this.availableChars = availableChars;
        return this;
    }
    
    /**
     * 
     * @param wikiHost base uri of wikipedia host
     * @return this object
     */
    public WikipediaService setWikiHost(String wikiHost) {
        this.wikiHost = wikiHost;
        return this;
    }
    
    /**
     * 
     * @param maxLength maximum length of keyword
     * @return this object
     */
    public WikipediaService setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }
    
    /**
     * 
     * @param maxRetries maximum amount of requests retries that can be done
     *  to wikipedia host
     * @return this object
     */
    public WikipediaService setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }
    
    /**
     * 
     * @param timeout amount of time in miliseconds between requests
     * @return this object
     */
    public WikipediaService setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
    
    /**
     * For sake of optimization more than one keyword is returned in single query,
     * in case it is invalid.
     * 
     * @param querySize amount of keywords returned in single query
     * @return this object
     */
    public WikipediaService setQuerySize(int querySize) {
        this.querySize = querySize;
        return this;
    }
    
    /**
     * Creates new hangman based on random keyword queried from wikipedia.
     * If no valid query is obtained exception is thrown.
     * 
     * @param lives amount of lives available for this hangman
     * @return hangman with wikipedia's keyword
     * @throws URISyntaxException if constructed request uri is invalid
     * @throws TimeoutException if no valid query could have been obtained
     * @throws InterruptedException if this thread is interrupted while in timeout
     */
    public Hangman createHangman(int lives) 
            throws URISyntaxException, TimeoutException, InterruptedException, 
            ClientProtocolException, IOException {
        
        JSONObject page = queryRandomPage();
        Hangman hangman = new Hangman()
                .createKeyword(page.getString("title"))
                .setActualLives(lives)
                .setMaxLives(lives);
        setArticleProperties(hangman, page.getLong("id"));
        return hangman;
    }
    
    /**
     * Creates query to wikipedia's api in order to obtain information from
     * article about keyword from given hangman.
     * 
     * @param hangman target hangman to assign article information
     * @param pageId id of queried article
     * @throws URISyntaxException if constructed wikipedia URI is invalid
     * @throws ClientProtocolException if http client protocol is invalid
     * @throws IOException if reading from response buffer encountered an error
     */
    private void setArticleProperties(Hangman hangman, Long pageId) throws
            URISyntaxException, ClientProtocolException, IOException {
        
        String id = Long.toString(pageId);
        
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(wikiHost)
                .setPath("/w/api.php")
                .setParameter("action", "query")
                .setParameter("format", "json")
                .setParameter("prop", "categories|extracts")
                .setParameter("pageids", id)
                .setParameter("clshow", "!hidden")
                .setParameter("exintro", "1")
                .setParameter("explaintext", "1")
                .build();
        
        Content content = Request
                .Get(uri)
                .connectTimeout(REQUEST_TIMEOUT)
                .socketTimeout(REQUEST_TIMEOUT)
                .execute()
                .returnContent();
        
        JSONObject json = new JSONObject(content.asString())
                .getJSONObject("query")
                .getJSONObject("pages")
                .getJSONObject(id);
        
        JSONArray categories = json.getJSONArray("categories");
        String category = categories.length() == 0 ? "No category" : categories
                .getJSONObject(0)
                .getString("title")
                .replaceFirst(".*(?=:):", ""); // only characters after `:` character
        
        hangman.setArticleInformation(json.getString("extract"));
        hangman.setArticleCategory(category);
    }
    
    /**
     * Queries wikipedia's api as long as valid keyword is returned. Requests
     * are sent in intervals of 1 second (wikipedia does not state any timeout
     * int robots.txt). 
     * 
     * @return JSON object containing information for creating new hangman
     * @throws TimeoutException when {@code maxRetries} amount of requests
     *  have failed to retrieve a vaild query
     * @throws URISyntaxException when invalid uri has been constructed
     * @throws InterruptedException if this thread is interrupted while in timeout
     * @throws ClientProtocolException if http client protocol is invalid
     * @throws IOException if reading from response buffer encountered an error
     */
    private JSONObject queryRandomPage() 
            throws URISyntaxException, TimeoutException, InterruptedException, 
            ClientProtocolException, IOException {
        
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(wikiHost)
                .setPath("/w/api.php")
                .setParameter("action", "query")
                .setParameter("format", "json")
                .setParameter("list", "random")
                .setParameter("rnnamespace", "0")
                .setParameter("rnlimit", Integer.toString(querySize))
                .build();
        
        for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
            
            Content content = Request
                .Get(uri)
                .connectTimeout(REQUEST_TIMEOUT)
                .socketTimeout(REQUEST_TIMEOUT)
                .execute()
                .returnContent();
            
            JSONObject json = new JSONObject(content.asString());
            JSONArray pages = json.getJSONObject("query").getJSONArray("random");
            JSONObject currentPage;
            int i;
            for (i = 0; i < querySize; i++) {
                currentPage = pages.getJSONObject(i);
                if (isKeywordValid(currentPage.getString("title"))) {
                    return currentPage;
                }
            }
            Thread.sleep(timeout);
        }
        
        throw new TimeoutException(
            String.format("No valid keyword has been obtained after %1$d retries.", maxRetries));
    }
    
    /**
     * Determines if keyword is valid specified by character set.
     * 
     * @param keyword keyword to validate
     * @return true if is valid, otherwise false
     */
    private boolean isKeywordValid(String keyword) {
        
        for (char ch : keyword.toCharArray()) {
            if (!availableChars.contains(Character.toUpperCase(ch))) {
                return false;
            }
        }
        return true;
    }
}
