package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

public record AccountEntity(String clientId,
                            String accountNumber,
                            String accountType,
                            Double initialBalance,
                            Boolean status) {
}