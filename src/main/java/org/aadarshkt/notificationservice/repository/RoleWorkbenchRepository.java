package org.aadarshkt.notificationservice.repository;

import org.aadarshkt.notificationservice.model.RoleWorkbench;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleWorkbenchRepository extends JpaRepository<RoleWorkbench, Long> {
}
