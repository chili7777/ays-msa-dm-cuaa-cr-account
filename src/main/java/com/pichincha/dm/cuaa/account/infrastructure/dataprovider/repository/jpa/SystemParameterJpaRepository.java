package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa;

import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.SystemParameterEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemParameterJpaRepository extends JpaRepository<SystemParameterEntity, Long> {
    Optional<SystemParameterEntity> findByCode(String code);
}