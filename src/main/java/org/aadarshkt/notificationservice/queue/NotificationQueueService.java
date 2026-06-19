package org.aadarshkt.notificationservice.queue;

import lombok.extern.slf4j.Slf4j;
import org.aadarshkt.notificationservice.config.RabbitMQConfig;
import org.aadarshkt.notificationservice.dto.NotificationEvent;
import org.aadarshkt.notificationservice.model.RoleLimit;
import org.aadarshkt.notificationservice.model.User;
import org.aadarshkt.notificationservice.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NotificationQueueService {

    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;

    public NotificationQueueService(RabbitTemplate rabbitTemplate, UserRepository userRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.userRepository = userRepository;
    }

    public void push(List<String> userIds, RoleLimit roleLimit) {
        for (String userId : userIds) {
            try {
                Optional<User> userOpt = userRepository.findById(userId);
                String emailId = userOpt.map(User::getEmailId).orElse(null);
                String phoneNumber = userOpt.map(User::getPhoneNumber).orElse(null);

                NotificationEvent event = NotificationEvent.builder()
                        .userId(userId)
                        .emailId(emailId)
                        .phoneNumber(phoneNumber)
                        .roleLimitId(roleLimit.getId())
                        .limitType(roleLimit.getAdministrativeLimit().getLimitType().name())
                        .limitDescription(roleLimit.getAdministrativeLimit().getLimitDescription())
                        .limitValue(roleLimit.getLimitValue())
                        .roleName(roleLimit.getRole().getRoleName())
                        .timestamp(LocalDateTime.now())
                        .build();

                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.EXCHANGE_NAME,
                        RabbitMQConfig.ROUTING_KEY,
                        event
                );
                
                log.info("Pushed notification event to queue for userId: {}", userId);
            } catch (Exception e) {
                log.error("Failed to push notification event for userId: {}", userId, e);
            }
        }
    }
}
