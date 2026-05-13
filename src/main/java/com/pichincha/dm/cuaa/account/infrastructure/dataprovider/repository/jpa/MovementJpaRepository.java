package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa;

import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.MovementEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovementJpaRepository extends JpaRepository<MovementEntity, String> {
    List<MovementEntity> findByAccountId(String accountId);
    void deleteByAccountId(String accountId);

    @Query("SELECT SUM(m.amount) FROM MovementEntity m WHERE m.accountId = :accountId AND m.movementType = :type AND m.movementDate >= :start AND m.movementDate <= :end AND m.status = true")
    Double sumAmountByAccountIdAndTypeAndDateBetween(String accountId, String type, LocalDateTime start, LocalDateTime end);
}