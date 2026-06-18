package org.aadarshkt.notificationservice.ruleengine;

import org.aadarshkt.notificationservice.model.RoleLimit;
import org.aadarshkt.notificationservice.repository.WorkbenchUsageRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MaxConcurrentRuleEvaluator implements RuleEvaluator {

    // Assumption: Limit ID for MAX_CONCURRENT is 1 in the administrative_limit table.
    private static final Long MAX_CONCURRENT_LIMIT_ID = 1L;
    
    private final WorkbenchUsageRepository workbenchUsageRepository;

    public MaxConcurrentRuleEvaluator(WorkbenchUsageRepository workbenchUsageRepository) {
        this.workbenchUsageRepository = workbenchUsageRepository;
    }

    @Override
    public boolean supports(Long limitId) {
        return MAX_CONCURRENT_LIMIT_ID.equals(limitId);
    }

    @Override
    public List<String> evaluateUsersExceedingLimit(RoleLimit roleLimit) {
        Long roleId = roleLimit.getRole().getId();
        Integer limitValue = Integer.parseInt(roleLimit.getLimitValue());
        
        return workbenchUsageRepository.findUsersExceedingMaxConcurrentLimit(roleId, limitValue);
    }
}
