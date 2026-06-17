package org.aadarshkt.notificationservice.repository;

import org.aadarshkt.notificationservice.model.WorkbenchUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkbenchUsageRepository extends JpaRepository<WorkbenchUsage, Long> {
}
