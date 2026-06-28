package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notification_log")
public class NotificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private String userId; // Generic user identifier (e.g. from any service)
    
    @Column(name = "event_type", nullable = false)
    private String eventType; // Matches the NotificationTemplate eventType
    
    @Column(name = "source_service")
    private String sourceService; // e.g., "booking-service", "auth-service"
    
    @Column(name = "channel")
    private String channel; // e.g., "EMAIL", "SMS", "IN_APP"
    
    @Column(name = "status")
    private String status; // e.g., "SUCCESS", "FAILED"
    
    @Column(name = "recipient_contact")
    private String recipientContact; // Email ID, Phone number, or FCM token used
    
    @Column(name = "resolved_message", columnDefinition = "TEXT")
    private String resolvedMessage; // The actual generated message content
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
