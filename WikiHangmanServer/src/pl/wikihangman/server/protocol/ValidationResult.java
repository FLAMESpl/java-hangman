package pl.wikihangman.server.protocol;

/**
 * Result of command options validation.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ValidationResult {

    private boolean valid;
    private String reason;
    
    private ValidationResult() {
        
    }
    
    /**
     * 
     * @return if command options were valid
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * 
     * @return validation error information if any, otherwise null
     */
    public String getReason() {
        return reason;
    }
    
    /**
     * Creates new validation result with null reason and true valid flag.
     * 
     * @return new {@code ValidationResult} object
     */
    public static ValidationResult success() {
        ValidationResult result = new ValidationResult();
        result.valid = true;
        return result;
    }
    
    /**
     * Creates new validation result with specified reason string and false 
     * valid flag.
     * 
     * @param reason information about invalid result cause
     * @return new {@code ValidationResult} object
     */
    public static ValidationResult fail(String reason) {
        ValidationResult result = new ValidationResult();
        result.valid = false;
        result.reason = reason;
        return result;
    }
}
