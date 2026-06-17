package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Workbench {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String workbenchName;
    
    private String description;
    
    private LocalDateTime createdAt;
}
