package org.aadarshkt.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.aadarshkt.notificationservice.config.RabbitMQConfig;
import org.aadarshkt.notificationservice.dto.NotificationEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, concurrency = "3-10")
    public void processNotification(NotificationEvent event) {
        log.info("Received notification event for User: {}, Limit: {}, Timestamp: {}", 
                event.getUserId(), event.getLimitType(), event.getTimestamp());

        // PLACEHOLDER: Generalized Notification Firing Logic
        // In the future, this can be extended to handle various notification channels.
        // For example, based on user preferences or the importance of the limit,
        // we can route to one or more of the following:

        if (event.getEmailId() != null) {
            sendEmailNotification(event);
        }

        if (event.getPhoneNumber() != null) {
            sendSmsNotification(event);
        }

        sendProductNotification(event);
        
        log.info("Successfully processed notification event for User: {}", event.getUserId());
    }

    private void sendEmailNotification(NotificationEvent event) {
        // PLACEHOLDER: Implementation for sending Email
        // e.g., using JavaMailSender or an external API like SendGrid
        log.debug("Placeholder: Sending EMAIL to {}", event.getEmailId());
    }

    private void sendSmsNotification(NotificationEvent event) {
        // PLACEHOLDER: Implementation for sending SMS
        // e.g., using Twilio API or AWS SNS
        log.debug("Placeholder: Sending SMS to {}", event.getPhoneNumber());
    }

    private void sendProductNotification(NotificationEvent event) {
        // PLACEHOLDER: Implementation for sending In-App / Product Notification
        // e.g., saving to a notifications table or pushing via WebSockets/FCM
        log.debug("Placeholder: Sending IN-APP notification to User {}", event.getUserId());
    }
}
