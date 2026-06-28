package org.aadarshkt.notificationservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aadarshkt.notificationservice.config.RabbitMQConfig;
import org.aadarshkt.notificationservice.dto.NotificationEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
//SRP -> To consume notification event and route them to correct handlers.
public class NotificationService {

    private final SseService sseService;
    private final FcmService fcmService;
    private final TemplateResolverService templateResolverService;

    //Consume notification event and route them to handlers.
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, concurrency = "3-10")
    public void processNotification(NotificationEvent event) {
        log.info("Received notification event for User: {}, EventType: {}, Timestamp: {}", 
                event.getUserId(), event.getEventType(), event.getTimestamp());

        // Use template engine to resolve the message body if templateData is present
        String resolvedMessage = "";
        if (event.getEventType() != null && event.getTemplateData() != null) {
            resolvedMessage = templateResolverService.resolveTemplate(event.getEventType(), event.getTemplateData());
            log.info("Resolved Message from Template: {}", resolvedMessage);
            // TODO: Pass `resolvedMessage` down to SSE, FCM, and Email services
        } else {
            // Legacy handling or missing template data
            log.warn("Event type or template data is missing. Using fallback logic.");
        }

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
        // TODO (Future): Implement Retries and DLQ (Dead Letter Queue) here if email fails
        // try { sendEmail(); } catch (Exception e) { throw new AmqpRejectAndDontRequeueException(e); } 
        // to route to DLQ instead of infinite requeue.
        log.debug("Placeholder: Sending EMAIL to {}", event.getEmailId());
    }

    private void sendSmsNotification(NotificationEvent event) {
        // PLACEHOLDER: Implementation for sending SMS
        // e.g., using Twilio API or AWS SNS
        log.debug("Placeholder: Sending SMS to {}", event.getPhoneNumber());
    }

    
    private void sendProductNotification(NotificationEvent event) {
        // Attempt to send via SSE first (if user is actively connected)
        boolean isSseSent = sseService.sendEventToUser(event);
        
        if (isSseSent) {
            log.debug("Successfully sent IN-APP notification to User {} via SSE", event.getUserId());
        } else {
            // Fallback to FCM if the user is not actively connected via SSE
            log.debug("User {} is not connected via SSE. Falling back to FCM.", event.getUserId());
            fcmService.sendNotification(event);
        }
    }
}
