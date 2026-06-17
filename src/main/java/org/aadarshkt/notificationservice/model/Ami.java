package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Ami {
    @Id
    private String amiId;
    
    private String baseOs;
    
    private String regions;
    
    private LocalDateTime createdAt;
}
