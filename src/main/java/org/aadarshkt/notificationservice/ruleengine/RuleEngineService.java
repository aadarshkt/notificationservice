package org.aadarshkt.notificationservice.ruleengine;

import lombok.extern.slf4j.Slf4j;
import org.aadarshkt.notificationservice.model.RoleLimit;
import org.aadarshkt.notificationservice.repository.RoleLimitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RuleEngineService {

    private final RuleEvaluatorFactory ruleEvaluatorFactory;
    private final RoleLimitRepository roleLimitRepository;
    private final org.aadarshkt.notificationservice.queue.NotificationQueueService notificationQueueService;

    public RuleEngineService(RuleEvaluatorFactory ruleEvaluatorFactory, RoleLimitRepository roleLimitRepository, org.aadarshkt.notificationservice.queue.NotificationQueueService notificationQueueService) {
        this.ruleEvaluatorFactory = ruleEvaluatorFactory;
        this.roleLimitRepository = roleLimitRepository;
        this.notificationQueueService = notificationQueueService;
    }

    /**
     * Evaluates all active role limits to identify users who have exceeded them.
     */
    public void evaluateAllRules() {
        log.info("Starting rule evaluation across all configured Role Limits.");
        List<RoleLimit> allLimits = roleLimitRepository.findAll();

        for (RoleLimit roleLimit : allLimits) {
            try {
                Long limitId = roleLimit.getAdministrativeLimit().getId();
                RuleEvaluator evaluator = ruleEvaluatorFactory.getEvaluator(limitId);
                
                List<String> userIdsExceedingLimit = evaluator.evaluateUsersExceedingLimit(roleLimit);
                
                if (!userIdsExceedingLimit.isEmpty()) {
                    log.info("Found {} users exceeding limit {} for role {}", 
                            userIdsExceedingLimit.size(), 
                            roleLimit.getAdministrativeLimit().getLimitType(), 
                            roleLimit.getRole().getRoleName());
                    
                    notificationQueueService.push(userIdsExceedingLimit, roleLimit);
                }
            } catch (Exception e) {
                log.error("Failed to evaluate rule for RoleLimit ID: {}", roleLimit.getId(), e);
            }
        }
        log.info("Completed rule evaluation.");
    }
}
