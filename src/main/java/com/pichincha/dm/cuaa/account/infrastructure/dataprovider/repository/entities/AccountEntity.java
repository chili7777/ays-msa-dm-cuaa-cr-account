package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

import java.util.UUID;

public record AccountEntity(UUID clientId,
                            String accountNumber,
                            String accountType,
                            Double initialBalance,
                            Boolean status) {
}