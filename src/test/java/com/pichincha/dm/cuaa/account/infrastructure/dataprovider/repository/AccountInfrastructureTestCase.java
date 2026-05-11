package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.shared.InfrastructureTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AccountInfrastructureTestCase extends InfrastructureTestCase {

    @Autowired
    protected InMemoryAccountRepository repository;
}