package pl.wikihangman.web.infrastructure.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.servlet.http.HttpServletRequest;

/**
 * Represents single predicate in request validation.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ValidationRule {

    private final List<ValidationRule> subsequentRules = new ArrayList<>();
    private Function<HttpServletRequest, String> errorMessageProvider;
    private Predicate<HttpServletRequest> predicate;
    
    /**
     * 
     * @param errorMessageProvider function providing error message based on its 
     *  request
     * @return this object
     */
    public ValidationRule setErrorMessage(
            Function<HttpServletRequest, String> errorMessageProvider) {
        
        this.errorMessageProvider = errorMessageProvider;
        return this;
    }
    
    /**
     * 
     * @param predicate predicate that will be checked in this rule
     * @return this object
     */
    public ValidationRule setPredicate(
            Predicate<HttpServletRequest> predicate) {
        
        this.predicate = predicate;
        return this;
    }

    /**
     * Adds rule to be checked only if this one is satisfied.
     * 
     * @param ruleBuilder fluent pipeline constructing rule
     * @return this object
     */
    public ValidationRule addSubsequent(Consumer<ValidationRule> ruleBuilder) {
        ValidationRule rule = new ValidationRule();
        ruleBuilder.accept(rule);
        subsequentRules.add(rule);
        return this;
    }
    
    /**
     * Tests request if it is valid.
     * 
     * @param request request to validate
     * @return true if request satisfies predicate, otherwise false
     */
    public ValidationResult test(HttpServletRequest request) {
        boolean valid = predicate.test(request);
        String errorMessage = valid ? null : errorMessageProvider.apply(request);
        ValidationResult validationResult = new ValidationResult(valid, errorMessage);
        
        if (!subsequentRules.isEmpty()) {
            List<ValidationResult> results = new ArrayList<>();
            subsequentRules.forEach(r -> results.add(r.test(request)));
            results.add(validationResult);
            return new ValidationResult(results);
        } else {
            return validationResult;
        }
    }
}
