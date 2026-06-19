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
    
    @Column(name = "email_id")
    private String emailId;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
