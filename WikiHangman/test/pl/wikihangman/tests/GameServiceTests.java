package pl.wikihangman.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.*;
import org.junit.Test;
import pl.wikihangman.exceptions.SessionTerminatedException;
import pl.wikihangman.models.Hangman;
import pl.wikihangman.services.GameService;
import pl.wikihangman.views.models.HangmanViewModel;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class GameServiceTests {
    
    @Test
    public void DiscoversLettersProperly() {
        
        GameService service = new GameService();
        int userId = 1;
        service.startNewSession(userId);
        List<Character> testCharacters = 
                new ArrayList<>(Arrays.asList('T', 'E', 'S', 'O', 'W', 'Y'));
        
        try {
            for(Character character : testCharacters) {
                assertDiscoveringLetter(service, userId, character);
            }
        } catch (SessionTerminatedException sessionTerminatedException) {
            fail("Session has been closed too early");
        }
    }
    
    private void assertDiscoveringLetter(GameService service, int userId, char character) 
        throws SessionTerminatedException {
        
        HangmanViewModel viewModel = service.discoverLetter(userId, character);
        boolean containsCharacter = viewModel.getKeyword()
                .stream()
                .flatMap(l -> Stream.of(l.getCharacter()))
                .anyMatch(c -> Character.toUpperCase(c) == character);
        assertEquals("Live was lost during discovering proper letter",
                viewModel.getActualLives(), viewModel.getMaxLives());
        assertTrue("Character was not discovered", containsCharacter);
    }
}
