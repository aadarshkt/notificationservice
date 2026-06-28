package org.aadarshkt.notificationservice.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.aadarshkt.notificationservice.dto.NotificationEvent;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//SRP -> build message from notification event and use fire notification
public class FcmService {

    public void sendNotification(NotificationEvent event) {
        try {
            // PLACEHOLDER: Fetch actual FCM token for the user from a database
            String dummyFcmToken = "dummy-fcm-token-for-" + event.getUserId();
            
            Message message = Message.builder()
                .putData("userId", event.getUserId() != null ? event.getUserId() : "")
                .putData("eventType", event.getEventType() != null ? event.getEventType() : "UNKNOWN")
                .setToken(dummyFcmToken)
                .build();
                
            FirebaseMessaging.getInstance().send(message);
            log.debug("Successfully sent FCM notification to User {}", event.getUserId());
        } catch (Exception e) {
            log.error("Failed to send FCM notification for user {}: {}", event.getUserId(), e.getMessage());
        }
    }
}
