package pl.wikihangman.web.tests;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import pl.wikihangman.web.models.Hangman;
import pl.wikihangman.web.models.Letter;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class HangmanTests {

    @Test
    public void discoversAllLettersProperly() {
        //given
        int lives = 3;
        Hangman hangman = new Hangman()
                .setActualLives(lives)
                .setMaxLives(lives)
                .createKeyword("Testowy");
        char[] characters = new char[] { 't', 'e', 'S', 'O', 'w', 'y' };
        
        //when
        for (int i = 0; i < characters.length; i++) {
            hangman.discover(characters[i]);
        }    
        
        //then
        assertEquals("Lost live in hangman when it should not", lives, hangman.getActualLives());
        assertFalse("Not all letters have been discovered" ,hangman.hasUndiscoveredLetters());
    }
    
    @Test
    public void losesLiveWhenMissesLetters() {
        //given
        int lives = 3;
        Hangman hangman = new Hangman()
                .setActualLives(lives)
                .setMaxLives(lives)
                .createKeyword("Testowy");
        
        //when
        hangman.discover('X');
        
        //then
        assertEquals("Live has not been lost", lives - 1, hangman.getActualLives());
    }
    
    @Test
    public void hasAnyLivesLeft() {
        //given
        int lives = 3;
        boolean hasAnyLivesBefore, hasAnyLivesAfter;
        Hangman hangman = new Hangman()
                .setActualLives(lives)
                .setMaxLives(lives)
                .createKeyword("Testowy");
        
        //when
        hasAnyLivesBefore = hangman.hasAnyLivesLeft();
        for (int i = 0; i < lives; i++) {
            hangman.discover('X');
        }
        hasAnyLivesAfter = hangman.hasAnyLivesLeft();
        
        //then
        assertTrue("Hangman should have any lives", hasAnyLivesBefore);
        assertFalse("Hangman should not have any lives", hasAnyLivesAfter);
    }
    
    @Test
    public void hasUndiscoveredLetters() {
        //given
        boolean hasUndiscoveredLettersAfter, hasUndiscoveredLettersBefore;
        Hangman hangman = new Hangman()
                .setActualLives(3)
                .setMaxLives(3)
                .createKeyword("Testowy");
        char[] characters = new char[] { 't', 'e', 's', 'o', 'w', 'y' };
        
        //when
        hasUndiscoveredLettersBefore = hangman.hasUndiscoveredLetters();
        for (int i = 0; i < characters.length; i++) {
            hangman.discover(characters[i]);
        }
        hasUndiscoveredLettersAfter = hangman.hasUndiscoveredLetters();
        
        //then
        assertTrue("Hangman should have undiscovred letters", hasUndiscoveredLettersBefore);
        assertFalse("Hangman should not have undiscovered letters", hasUndiscoveredLettersAfter);
    }
    
    @Test
    public void createsProperKeyword() {
        //given
        String keyword = "Testowy";
        
        //when
        Hangman hangman = new Hangman().createKeyword(keyword);
        
        //then
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
