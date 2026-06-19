package org.aadarshkt.notificationservice.repository;

import org.aadarshkt.notificationservice.model.WorkbenchUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkbenchUsageRepository extends JpaRepository<WorkbenchUsage, Long> {

    //TODO - this can also be handled when a new workbench instance is created, we directly prevent the user 
    //from creating.
    @Query(value = "SELECT wu.user_id " +
                   "FROM workbench_usage wu " +
                   "JOIN \"user\" u ON u.id = wu.user_id " +
                   "WHERE u.role_id = :roleId " +
                   "AND wu.stopped_at IS NULL " +
                   "GROUP BY wu.user_id " +
                   "HAVING COUNT(wu.id) > :limitValue", 
           nativeQuery = true)
    List<String> findUsersExceedingMaxConcurrentLimit(@Param("roleId") Long roleId, @Param("limitValue") Integer limitValue);

    @Query(value = "SELECT wu.user_id " +
                   "FROM workbench_usage wu " +
                   "JOIN \"user\" u ON u.id = wu.user_id " +
                   "WHERE u.role_id = :roleId " +
                   "AND wu.started_at >= :startOfDay " +
                   "GROUP BY wu.user_id " +
                   "HAVING SUM(EXTRACT(EPOCH FROM (COALESCE(wu.stopped_at, CURRENT_TIMESTAMP) - wu.started_at))/60) > :limitValue", 
           nativeQuery = true)
    List<String> findUsersExceedingMaxDailyTimeLimit(@Param("roleId") Long roleId, @Param("startOfDay") java.time.LocalDateTime startOfDay, @Param("limitValue") Integer limitValue);
}
