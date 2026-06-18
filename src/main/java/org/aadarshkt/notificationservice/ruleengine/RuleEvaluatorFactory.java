package org.aadarshkt.notificationservice.ruleengine;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RuleEvaluatorFactory {

    private final List<RuleEvaluator> evaluators;

    public RuleEvaluatorFactory(List<RuleEvaluator> evaluators) {
        this.evaluators = evaluators;
    }

    /**
     * Gets the evaluator that supports the specified limit id.
     * @param limitId The ID of the AdministrativeLimit.
     * @return The RuleEvaluator strategy for the limit, or throws exception if not found.
     */
    public RuleEvaluator getEvaluator(Long limitId) {
        return evaluators.stream()
                .filter(evaluator -> evaluator.supports(limitId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No RuleEvaluator found for limitId: " + limitId));
    }
}
