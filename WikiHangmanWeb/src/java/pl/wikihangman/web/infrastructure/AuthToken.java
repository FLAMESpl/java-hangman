package pl.wikihangman.web.infrastructure;

import javax.servlet.http.Cookie;
import pl.wikihangman.web.models.User;

/**
 * Authentication token as extension to cookie class.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AuthToken extends Cookie {

    /**
     * Looks for authentication token in cookies array.
     * 
     * @param cookies array of request cookies
     * @return {@code AuthToken} found, otherwise null
     */
    public static AuthToken getFrom(Cookie[] cookies) {
        
        if (cookies == null)
            return null;
        int i;
        for (i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("auth")) {
                break;
            }
        }
        return i != cookies.length ? new AuthToken(cookies[i].getValue()) : null;
    }
    
    /**
     * 
     * @param user user that is authenticated
     */
    public AuthToken(User user) {
        super("auth", Integer.toString(user.getId()));
    }
    
    /**
     * 
     * @param id id of user that is authenticated
     */
    public AuthToken(String id) {
        super("auth", id);
    }
    
    /**
     * Creates empty auth token
     */
    public AuthToken() {
        super("auth", "");
    }
    
    /**
     * Sets token's max age property to zero, so it will be deleted from user's
     * browser.
     * 
     * @return this object
     */
    public AuthToken delete() {
        setMaxAge(0);
        return this;
    }
}
