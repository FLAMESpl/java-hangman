package pl.wikihangman.web.views;

import java.util.HashMap;

/**
 * Simple wrapper for {@code HashMap<Character, Boolean>} that implements 
 * marker interface {@code UsedLettersMap}.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class UsedLettersHashMap extends HashMap<Character, Boolean> implements UsedLettersMap {

}
