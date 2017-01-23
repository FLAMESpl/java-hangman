package pl.wikihangman.web.infrastructure.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Result of {@code HttpServletRequest} validation containing additional
 * information about encountered errors.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ValidationResult {

    private final boolean valid;
    private final List<String> errors = new ArrayList<>();
    
    /**
     * Constructs result for single rule, if error is null nothing is added to
     * list of errors.
     * 
     * @param valid result of validation
     * @param error validation error description
     */
    public ValidationResult(boolean valid, String error) {
        this.valid = valid;
        if (error != null) {
            this.errors.add(error);
        }
    }
    
    /**
     * Merges multiple validation results into one.
     * 
     * @param validationResuls collection of validation results
     */
    public ValidationResult(List<ValidationResult> validationResuls) {
        boolean result = true;
        for (ValidationResult validationResult : validationResuls) {
            if (!validationResult.isValid()) {
                result = false;
                errors.addAll(validationResult.getErrors());
            }
        }
        valid = result;
    }
    
    /**
     * Tells if validation was successful
     * 
     * @return true if request was valid, otherwise false
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * Returns list of error related messages if was any
     * 
     * @return errors messages produced during validation
     */
    public List<String> getErrors() {
        return errors;
    }
}
