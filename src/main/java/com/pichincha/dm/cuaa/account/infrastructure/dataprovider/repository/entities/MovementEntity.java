package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementEntity {
    @Id
    @Column(length = 36, nullable = false)
    private String movementId;

    @Column(length = 36, nullable = false)
    private String accountId;

    @Column(nullable = false)
    private LocalDateTime movementDate;

    @Column(nullable = false, length = 20)
    private String movementType;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Boolean status;

    @Column(length = 255)
    private String description;
}