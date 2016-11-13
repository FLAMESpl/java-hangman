package pl.wikihangman.tests;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.wikihangman.controllers.AuthenticationController;
import pl.wikihangman.infrastructure.StreamHelper;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AuthenticationControllerTests {
    private static final String dbPath = ".\\test_database.txt";
    
    @BeforeClass
    public static void SetupFreshDatabase() {
        File file = new File(dbPath);
        try {
            file.createNewFile();
        } catch (IOException exception) {
            System.err.println("Could not setup test database");
        }
    }
    
    @AfterClass
    public static void CleanDatabase() {
        File file = new File(dbPath);
        file.delete();
    }
    
    @Test
    public void AddUserToDatabaseSuccessfuly() {
        AuthenticationController authController = new AuthenticationController(dbPath);
        StreamHelper.prepareSequence("user", "password");
        authController.register();
    }
}
