package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "\"user\"")
@Data
public class User {
    @Id
    private String id;
    
    private String username;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
