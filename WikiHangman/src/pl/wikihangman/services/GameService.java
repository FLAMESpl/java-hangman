package pl.wikihangman.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import pl.wikihangman.exceptions.SessionTerminatedException;
import pl.wikihangman.models.Hangman;
import pl.wikihangman.views.models.HangmanViewModel;

/**
 * {@code GameService} allows creating new game sessions, controlling theirs
 * flow and returning current states to views.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class GameService {
    
    private List<GameSession> activeSessions = new ArrayList<>();
    private List<Character> availableCharacters = new ArrayList<>();
    
    /**
     * Hangman for development purposes since there is no wikipedia service 
     * implemented.
     */
    private static final Supplier<Hangman> TEST_HANGMAN = () -> new Hangman()
                .setMaxLives(6)
                .setActualLives(6)
                .createKeyword("Testowy");
    
    /**
     * Starts new session for user of given id and creates new keyword.
     * 
     * @param userId requesting user's ids
     * @return view-model of actual hangman
     */
    public HangmanViewModel startNewSession(int userId) {
        Hangman hangman = TEST_HANGMAN.get();
        GameSession session = new GameSession()
                .setUserId(userId)
                .setHangman(hangman);
        activeSessions.add(session);
        return createHangmanViewModel(hangman);
    }
    
    /**
     * Discovers all letters of given type in keyword of given user's session.
     * If game-ending condition is met, eg. all lives are spent or all letters
     * are discovered, session is automatically removed.
     * 
     * @param userId id of session requesting user
     * @param character character to be discovered
     * @return view-model of actual hangman
     * @throws SessionTerminatedException 
     */
    public HangmanViewModel discoverLetter(int userId, char character) 
        throws SessionTerminatedException {
        
        GameSession session = activeSessions.stream()
                .filter(x -> x.getUserId() == userId)
                .findAny()
                .orElseThrow(() -> new SessionTerminatedException());
        
        Hangman hangman = session.getHangman();
        int discovered = hangman.discover(character);
        if (endgameConditionsAreMet(hangman)) {
            activeSessions.remove(session);
        }
        return createHangmanViewModel(hangman);
    }
    
    /**
     * Closes given user's session before its completion.
     * 
     * @param userId id of requesting user
     */
    public void closeSession(int userId) {
       activeSessions.removeIf(s -> s.getUserId() == userId);
    }
    
    /**
     * Translates domain hangman model to its view-model.
     * 
     * @param hangman domain hangman model
     * @return view-model of given domain model
     */
    private HangmanViewModel createHangmanViewModel(Hangman hangman) {
        return new HangmanViewModel()
                .setMaxLives(hangman.getMaxLives())
                .setActualLives(hangman.getActualLives())
                .createKeyword(hangman.getKeyword());
    }
    
    /**
     * Checks if endgame conditions are met for given {@code Hangman}.
     * It includes zero actual lives and no more letters to be discovered.
     * 
     * @param hangman {@code Hangman} to be tested
     * @return true if endgame conditions are met, otherwise false
     */
    private boolean endgameConditionsAreMet(Hangman hangman) {
        return hangman.getActualLives() == 0 || !hangman.hasUndiscoveredLetters();
    }
}
