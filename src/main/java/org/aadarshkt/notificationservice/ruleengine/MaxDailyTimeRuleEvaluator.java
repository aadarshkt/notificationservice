package org.aadarshkt.notificationservice.ruleengine;

import org.aadarshkt.notificationservice.model.RoleLimit;
import org.aadarshkt.notificationservice.repository.WorkbenchUsageRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MaxDailyTimeRuleEvaluator implements RuleEvaluator {

    // Assumption: Limit ID for MAX_DAILY_TIME is 2 in the administrative_limit table.
    private static final Long MAX_DAILY_TIME_LIMIT_ID = 2L;

    private final WorkbenchUsageRepository workbenchUsageRepository;

    public MaxDailyTimeRuleEvaluator(WorkbenchUsageRepository workbenchUsageRepository) {
        this.workbenchUsageRepository = workbenchUsageRepository;
    }

    @Override
    public boolean supports(Long limitId) {
        return MAX_DAILY_TIME_LIMIT_ID.equals(limitId);
    }

    @Override
    public List<String> evaluateUsersExceedingLimit(RoleLimit roleLimit) {
        Long roleId = roleLimit.getRole().getId();
        Integer limitValue = Integer.parseInt(roleLimit.getLimitValue());
        
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        
        return workbenchUsageRepository.findUsersExceedingMaxDailyTimeLimit(roleId, startOfDay, limitValue);
    }
}
