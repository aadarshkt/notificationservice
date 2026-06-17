package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class NotificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private User receiverUser;
    
    @ManyToOne
    @JoinColumn(name = "workbench_id")
    private Workbench workbench;
    
    private String instanceId;
    
    @ManyToOne
    @JoinColumn(name = "limit_id")
    private AdministrativeLimit administrativeLimit;
    
    private LocalDateTime createdAt;
}
