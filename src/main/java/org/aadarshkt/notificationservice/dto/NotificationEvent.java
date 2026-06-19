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
    private Long roleLimitId;
    private String limitType;
    private String limitDescription;
    private String limitValue;
    private String roleName;
    private LocalDateTime timestamp;
}
