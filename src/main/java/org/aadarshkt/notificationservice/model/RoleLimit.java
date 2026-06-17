package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RoleLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @ManyToOne
    @JoinColumn(name = "limit_type_id")
    private AdministrativeLimit administrativeLimit;
    
    private String limitValue;
}
