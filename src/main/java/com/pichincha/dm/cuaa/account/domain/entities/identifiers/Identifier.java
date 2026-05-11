package com.pichincha.dm.cuaa.account.domain.entities.identifiers;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class Identifier {

    @Getter
    private final String value;
}