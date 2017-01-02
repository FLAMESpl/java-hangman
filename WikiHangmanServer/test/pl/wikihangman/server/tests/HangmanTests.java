package pl.wikihangman.server.tests;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.Letter;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class HangmanTests {

    @Test
    public void discoversAllLettersProperly() {
        int lives = 3;
        Hangman hangman = new Hangman()
                .setActualLives(lives)
                .setMaxLives(lives)
                .createKeyword("Testowy");
        char[] characters = new char[] { 't', 'e', 'S', 'O', 'w', 'y' };
        
        for (int i = 0; i < characters.length; i++) {
            hangman.discover(characters[i]);
        }    
        
        assertEquals("Lost live in hangman when it should not", lives, hangman.getActualLives());
        assertFalse("Not all letters have been discovered" ,hangman.hasUndiscoveredLetters());
    }
    
    @Test
    public void losesLiveWhenMissesLetters() {
        int lives = 3;
        Hangman hangman = new Hangman()
                .setActualLives(lives)
                .setMaxLives(lives)
                .createKeyword("Testowy");
        
        hangman.discover('X');
        
        assertEquals("Live has not been lost", lives - 1, hangman.getActualLives());
    }
    
    @Test
    public void hasAnyLivesLeft() {
        int lives = 3;
        boolean hasAnyLivesBefore, hasAnyLivesAfter;
        Hangman hangman = new Hangman()
                .setActualLives(lives)
                .setMaxLives(lives)
                .createKeyword("Testowy");
        
        hasAnyLivesBefore = hangman.hasAnyLivesLeft();
        for (int i = 0; i < lives; i++) {
            hangman.discover('X');
        }
        hasAnyLivesAfter = hangman.hasAnyLivesLeft();
        
        assertTrue("Hangman should have any lives", hasAnyLivesBefore);
        assertFalse("Hangman should not have any lives", hasAnyLivesAfter);
    }
    
    @Test
    public void hasUndiscoveredLetters() {
        boolean hasUndiscoveredLettersAfter, hasUndiscoveredLettersBefore;
        Hangman hangman = new Hangman()
                .setActualLives(3)
                .setMaxLives(3)
                .createKeyword("Testowy");
        char[] characters = new char[] { 't', 'e', 's', 'o', 'w', 'y' };
        
        hasUndiscoveredLettersBefore = hangman.hasUndiscoveredLetters();
        for (int i = 0; i < characters.length; i++) {
            hangman.discover(characters[i]);
        }
        hasUndiscoveredLettersAfter = hangman.hasUndiscoveredLetters();
        
        assertTrue("Hangman should have undiscovred letters", hasUndiscoveredLettersBefore);
        assertFalse("Hangman should not have undiscovered letters", hasUndiscoveredLettersAfter);
    }
    
    @Test
    public void createsProperKeyword() {
        String keyword = "Testowy";
        
        Hangman hangman = new Hangman().createKeyword(keyword);
        
        assertKeywordCharacters("Keyword template and created one do not have common letters", 
                keyword, hangman.getKeyword());
        assertKeywordDiscoveredState("All keyword letters should be undiscovered", 
                hangman.getKeyword());
    }
    
    private void assertKeywordCharacters(
            String message, String keywordTemplate, List<Letter> createdKeyword) {
        
        if (keywordTemplate.length() != createdKeyword.size()) {
            fail(message);
        }
        
        for (int i = 0; i < keywordTemplate.length(); i++) {
            if (keywordTemplate.charAt(i) != createdKeyword.get(i).getCharacter()) {
                fail(message);
            }
        }
    }
    
    private void assertKeywordDiscoveredState(
            String message, List<Letter> createdKeyword) {
        
        for (int i = 0; i < createdKeyword.size(); i++) {
            if (createdKeyword.get(i).isDiscovered()) {
                fail(message);
            }
        }
    }
}
