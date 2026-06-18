package org.aadarshkt.notificationservice.ruleengine;

import org.aadarshkt.notificationservice.model.RoleLimit;
import java.util.List;

public interface RuleEvaluator {
    
    /**
     * Checks if this evaluator supports the given limit id.
     * @param limitId The ID of the AdministrativeLimit.
     * @return true if supported, false otherwise.
     */
    boolean supports(Long limitId);
    
    /**
     * Evaluates the rule limit against all users.
     * Assumption: limitValue in RoleLimit can be parsed to an Integer/Long (purely numeric).
     * @param roleLimit The role limit configuration to evaluate against.
     * @return List of user IDs that exceed the given role limit.
     */
    List<String> evaluateUsersExceedingLimit(RoleLimit roleLimit);
}
