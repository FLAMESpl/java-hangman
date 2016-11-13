package pl.wikihangman.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import pl.wikihangman.exceptions.FileException;
import pl.wikihangman.models.Score;

/**
 *
 * @author Łukasz Szafirski
 */
public class ScoreService {
    
    public List<Score> scoresFromFile(String dbPath) throws FileException {
     
        List<Score> scores = new ArrayList<>();
        try(FileInputStream in = new FileInputStream(dbPath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                Score score = new Score(words[1], Long.parseLong(words[3]));
                scores.add(score);
            }
        } catch (IOException | IndexOutOfBoundsException | NumberFormatException fileExceptionCause) {
            throw new FileException("Error occured while reading database file", fileExceptionCause);
        }
        return scores;
    }
}
