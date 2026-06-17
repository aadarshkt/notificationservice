package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RoleWorkbench {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @ManyToOne
    @JoinColumn(name = "workbench_id")
    private Workbench workbench;
}
