package org.aadarshkt.notificationservice.repository;

import org.aadarshkt.notificationservice.model.AdministrativeLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativeLimitRepository extends JpaRepository<AdministrativeLimit, Long> {
}
