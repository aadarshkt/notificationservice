package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AdministrativeLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private LimitType limitType;
    
    private String unit;
    
    private String limitDescription;
    
    private String defaultValue;
    
    public enum LimitType {
        MAX_CONCURRENT,
        MAX_DAILY_TIME
    }
}
