package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.ClientId;

public record AccountEntity(ClientId clientId,
                            String accountNumber,
                            String accountType,
                            Double initialBalance,
                            Boolean status) {
}