package org.aadarshkt.notificationservice.repository;

import org.aadarshkt.notificationservice.model.Ami;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmiRepository extends JpaRepository<Ami, String> {
}
