package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa;

import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.MovementEntity;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementJpaRepository extends JpaRepository<MovementEntity, String> {
    List<MovementEntity> findByAccountId(String accountId);
    void deleteByAccountId(String accountId);
}