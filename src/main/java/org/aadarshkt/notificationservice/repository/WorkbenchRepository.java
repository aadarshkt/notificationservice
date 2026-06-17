package org.aadarshkt.notificationservice.repository;

import org.aadarshkt.notificationservice.model.Workbench;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkbenchRepository extends JpaRepository<Workbench, Long> {
}
