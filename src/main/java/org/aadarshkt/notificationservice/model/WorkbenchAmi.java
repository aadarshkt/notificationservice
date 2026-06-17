package org.aadarshkt.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WorkbenchAmi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "workbench_id")
    private Workbench workbench;
    
    @ManyToOne
    @JoinColumn(name = "ami_id")
    private Ami ami;
}
