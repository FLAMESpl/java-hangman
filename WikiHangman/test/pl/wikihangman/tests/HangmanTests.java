package pl.wikihangman.tests;

import java.util.stream.Stream;
import static org.junit.Assert.*;
import org.junit.Test;
import pl.wikihangman.models.Hangman;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class HangmanTests {

    @Test
    public void discoversLettersProperly() {
        
        Hangman hangman = new Hangman()
                .createKeyword("TEST")
                .setActualLives(1)
                .setMaxLives(1);
        
        assertDiscoveringLetters(hangman, 'T');
        assertDiscoveringLetters(hangman, 'E');
        assertDiscoveringLetters(hangman, 'S');
    }
    
    @Test 
    public void discoveringLettersIsCaseInsensitive() {
        
        Hangman hangman = new Hangman()
                .createKeyword("CaSe")
                .setActualLives(1)
                .setMaxLives(1);
        
        assertDiscoveringLetters(hangman, 'C');
        assertDiscoveringLetters(hangman, 'a');
        assertDiscoveringLetters(hangman, 's');
        assertDiscoveringLetters(hangman, 'E');
    }
    
    @Test
    public void missingLettersLosesLives() {
        
        Hangman hangman = new Hangman()
                .createKeyword("a")
                .setActualLives(1)
                .setMaxLives(1);
        
        hangman.discover('b');
        assertEquals("Discovering non-existing letter did not decreased lives amount",
                0, hangman.getActualLives());
    }
    
    @Test
    public void hittingLettersDoesNotLoseLives() {
        
        Hangman hangman = new Hangman()
                .createKeyword("a")
                .setActualLives(1)
                .setMaxLives(1);
        
        hangman.discover('a');
        assertEquals("Discovering existing letter decreased lives amount",
                hangman.getMaxLives(), hangman.getActualLives());
    }
    
    private void assertDiscoveringLetters(Hangman hangman, char character) {
        
        hangman.discover(character);
        boolean anyMatch = hangman.getKeyword()
                .stream()
                .flatMap(l -> Stream.of(l.getCharacter()))
                .anyMatch(c -> Character.toUpperCase(c) == Character.toUpperCase(character));
        
        assertTrue("Letter has not been discovered properly", anyMatch);
    }
}
