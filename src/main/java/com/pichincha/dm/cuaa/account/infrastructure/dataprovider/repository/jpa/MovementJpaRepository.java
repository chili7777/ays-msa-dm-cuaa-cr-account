package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa;

import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.MovementEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface MovementJpaRepository extends JpaRepository<MovementEntity, String> {
    List<MovementEntity> findByAccountId(String accountId);
    void deleteByAccountId(String accountId);

    @Query("SELECT SUM(m.amount) FROM MovementEntity m WHERE m.accountId = :accountId AND m.movementType = :type AND m.movementDate >= :start AND m.movementDate <= :end AND m.status = true")
    Double sumAmountByAccountIdAndTypeAndDateBetween(@Param("accountId") String accountId, @Param("type") String type, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<MovementEntity> findByAccountIdAndMovementDateBetween(String accountId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT m FROM MovementEntity m WHERE " +
           "(cast(:accountId as string) IS NULL OR m.accountId = :accountId) AND " +
           "(cast(:start as timestamp) IS NULL OR m.movementDate >= :start) AND " +
           "(cast(:end as timestamp) IS NULL OR m.movementDate <= :end) AND " +
           "(cast(:type as string) IS NULL OR m.movementType = :type)")
    List<MovementEntity> findByFilters(@Param("accountId") String accountId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("type") String type);
}