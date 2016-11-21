package pl.wikihangman.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import pl.wikihangman.exceptions.EntityAlreadyExistsException;
import pl.wikihangman.models.User;
import pl.wikihangman.services.AccountsService;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AccountServiceTests {
    
    private static final String DB_PATH = ".\\test-database.txt";
    
    @Before
    public void createFreashDatabaseFile() {
        File f = new File(DB_PATH);
        try {
            f.createNewFile();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }
    
    @After
    public void removeDatabaseFile() {
        File f = new File(DB_PATH);
        System.gc();
        f.delete();
    }
    
    @Test
    public void addsNewUserToDatabase() {
        
        AccountsService service = new AccountsService(DB_PATH);
        try {
            service.register("alfa", "omega");
        } catch (Exception exception) {
            fail("Could not add new user to database file");
        }
    }
    
    @Test
    public void userNameCanNotDuplicate() {
        
        AccountsService service = new AccountsService(DB_PATH);
        try {
            service.register("alfa", "omega");
            service.register("alfa", "delta");
        } catch (EntityAlreadyExistsException alreadyExists) {
            return;
        } catch (Exception exception) {
            fail("Could not add new user to database file");
        }
        fail("Users of two identical names have been added");
    }
    
    @Test
    public void readsFromDatabaseFile() {
        
        AccountsService service = new AccountsService(DB_PATH);
        User user = null;
        try {
            service.register("alfa", "omega");
            user = service.authenticate("alfa", "omega");
        } catch (Exception ex) {
            fail("Could not add new user or read database file");
        }
        assertNotNull("Authentication failed", user);
    }
    
    @Test
    public void addingUsersGeneratesUniqueIds() {
        
        int usersAdded = 5;
        AccountsService service = new AccountsService(DB_PATH);
        Set<Integer> ids = new HashSet<>();
        try {
            for (Integer i = 0; i < usersAdded; i++) {
                User user = service.register(i.toString(), "pass");
                ids.add(user.getId());
            }
        } catch (Exception ex) {
            fail("Could not add user to database");
        }
        
        assertEquals("Some ids are not unique", usersAdded, ids.size());
    }
}
