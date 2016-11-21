package pl.wikihangman.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import static org.junit.Assert.*;
import org.junit.Test;
import pl.wikihangman.exceptions.SessionTerminatedException;
import pl.wikihangman.services.GameService;
import pl.wikihangman.views.models.HangmanViewModel;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class GameServiceTests {
    
    @Test
    public void createsViewModelWithHiddenUndiscoveredLetters() {
        
        GameService service = new GameService();
        int userId = 1;
        HangmanViewModel viewModel = service.startNewSession(userId);
        assertList("Not all letters are hidden in view-model", viewModel.getKeyword(), l -> l.getCharacter() == '\0');
    }
    
    @Test
    public void discoversLettersProperly() {
        
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
    
    @Test
    public void TerminatesSessionOnDemandProperly() {
        
        GameService service = new GameService();
        int userId = 1;
        service.startNewSession(userId);
        service.closeSession(userId);
        try {
            assertDiscoveringLetter(service, userId, 'T');
            fail("Session hasn't been terminated");
        } catch (SessionTerminatedException sessionTerminatedException) {
            
        }
    }
    
    @Test
    public void TerminatesSessionOnHangmanComplete() {
        
        GameService service = new GameService();
        int userId = 1;
        service.startNewSession(userId);
        List<Character> testCharacters = 
                new ArrayList<>(Arrays.asList('T', 'E', 'S', 'O', 'W', 'Y', 'Y'));
        
        try {
            for(Character character : testCharacters) {
                assertDiscoveringLetter(service, userId, character);
            }
            fail("Session has not been terminated on hangman completion");
        } catch (SessionTerminatedException sessionTerminatedException) {
            
        }
    }
    
    @Test
    public void TerminatesSessionsWhenAllLivesLost() {
        
        GameService service = new GameService();
        int userId = 1;
        int maxLives = service.startNewSession(userId).getMaxLives();
        
        try {
            for(int i = maxLives; i >= 0; i--) {
                assertLosingLive(service, userId, 'X', i - 1);
            }
            fail("Session has not been terminated when all lives were lost");
        } catch (SessionTerminatedException sessionTerminatedException) {
            
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
                viewModel.getMaxLives(), viewModel.getActualLives());
        assertTrue("Character was not discovered", containsCharacter);
    }
    
    private void assertLosingLive(GameService service, int userId, char character, int expectedLives) 
        throws SessionTerminatedException {
        
        HangmanViewModel viewModel = service.discoverLetter(userId, character);
        assertEquals("Live was not lost on missed letter", expectedLives, viewModel.getActualLives());
    }
    
    private <E> void assertList(String message, List<E> list, Function<E, Boolean> predicate) {
        list.forEach(e -> assertTrue(message, predicate.apply(e)));
    }
}
