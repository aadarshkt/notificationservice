package org.aadarshkt.notificationservice.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aadarshkt.notificationservice.config.RabbitMQConfig;
import org.aadarshkt.notificationservice.dto.NotificationEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SseService sseService;

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
        // Attempt to send via SSE first (if user is actively connected)
        boolean isSseSent = sseService.sendEventToUser(event.getUserId(), event);
        
        if (isSseSent) {
            log.debug("Successfully sent IN-APP notification to User {} via SSE", event.getUserId());
        } else {
            // Fallback to FCM if the user is not actively connected via SSE
            log.debug("User {} is not connected via SSE. Falling back to FCM.", event.getUserId());
            sendFcmNotification(event);
        }
    }

    private void sendFcmNotification(NotificationEvent event) {
        try {
            // PLACEHOLDER: Fetch actual FCM token for the user from a database
            String dummyFcmToken = "dummy-fcm-token-for-" + event.getUserId();
            
            Message message = Message.builder()
                .putData("userId", event.getUserId() != null ? event.getUserId() : "")
                .putData("limitType", event.getLimitType() != null ? event.getLimitType() : "UNKNOWN")
                .setToken(dummyFcmToken)
                .build();
                
            FirebaseMessaging.getInstance().send(message);
            log.debug("Successfully sent FCM notification to User {}", event.getUserId());
        } catch (Exception e) {
            log.error("Failed to send FCM notification for user {}: {}", event.getUserId(), e.getMessage());
        }
    }
}
