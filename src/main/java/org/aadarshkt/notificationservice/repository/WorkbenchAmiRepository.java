package org.aadarshkt.notificationservice.repository;

import org.aadarshkt.notificationservice.model.WorkbenchAmi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkbenchAmiRepository extends JpaRepository<WorkbenchAmi, Long> {
}
