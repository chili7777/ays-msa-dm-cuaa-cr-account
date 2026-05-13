package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @Column(length = 36, nullable = false)
    private String accountId;

    @Column(length = 36, nullable = false)
    private String clientId;

    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(nullable = false, length = 20)
    private String accountType;

    @Column(nullable = false)
    private Double initialBalance;

    @Column(nullable = false)
    private Boolean status;
}