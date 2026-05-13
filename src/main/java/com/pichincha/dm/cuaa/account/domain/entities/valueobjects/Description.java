package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a movement description.
 */
@Value
public class Description implements ValueObject<String> {

    String value;

    public Description(String value) {
        this.value = value != null ? value : "";
    }

}