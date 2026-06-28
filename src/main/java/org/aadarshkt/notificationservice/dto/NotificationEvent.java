package org.aadarshkt.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String userId;
    private String emailId;
    private String phoneNumber;
    
    // Generic Envelope Fields
    private String eventType;
    private String sourceService;
    private java.util.Map<String, Object> templateData;
    
    private LocalDateTime timestamp;
}
