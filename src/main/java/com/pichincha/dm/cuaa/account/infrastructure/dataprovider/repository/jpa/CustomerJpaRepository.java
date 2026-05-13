package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa;

import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.ClientEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<ClientEntity, String> {
    Optional<ClientEntity> findByIdentification(String identification);
}