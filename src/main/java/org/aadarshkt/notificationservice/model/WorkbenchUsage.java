package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class WorkbenchUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "workbench_id")
    private Workbench workbench;
    
    private String instanceId;
    
    private LocalDateTime startedAt;
    
    private LocalDateTime stoppedAt;
}
