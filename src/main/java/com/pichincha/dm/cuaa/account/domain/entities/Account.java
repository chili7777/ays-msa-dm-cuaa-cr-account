package com.pichincha.dm.cuaa.account.domain.entities;

import java.util.UUID;

public record Account(UUID clientId,
                      String accountNumber,
                      String accountType,
                      Double initialBalance,
                      Boolean status) {
}