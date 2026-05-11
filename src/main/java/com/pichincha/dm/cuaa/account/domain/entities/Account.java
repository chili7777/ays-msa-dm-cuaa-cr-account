package com.pichincha.dm.cuaa.account.domain.entities;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.ClientId;

public record Account(ClientId clientId,
                      String accountNumber,
                      String accountType,
                      Double initialBalance,
                      Boolean status) {
}