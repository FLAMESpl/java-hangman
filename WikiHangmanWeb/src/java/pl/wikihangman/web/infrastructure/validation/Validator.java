package pl.wikihangman.web.infrastructure.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.servlet.http.HttpServletRequest;

/**
 * Determines if user request is correct by specified rules.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Validator {

    private final List<ValidationRule> rules = new ArrayList<>();
    
    /**
     * Uses defined set of rules to determine if request is valid.
     * 
     * @param request request to test
     * @return true if is valid, otherwise false
     */
    public ValidationResult test(HttpServletRequest request) {
        List<ValidationResult> validationResults = new ArrayList<>();
        for (ValidationRule rule : rules) {
            ValidationResult validationResult = rule.test(request);
            validationResults.add(validationResult);
        }
        return new ValidationResult(validationResults);
    }
    
    /**
     * 
     * @param ruleBuilder fluent pipeline constructing a rule
     * @return this object
     */
    public Validator addRule(Consumer<ValidationRule> ruleBuilder) {
        ValidationRule rule = new ValidationRule();
        ruleBuilder.accept(rule);
        rules.add(rule);
        return this;
    }
}
