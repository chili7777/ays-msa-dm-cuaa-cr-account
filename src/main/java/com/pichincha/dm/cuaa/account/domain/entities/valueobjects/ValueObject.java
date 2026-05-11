package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

/**
 * Common contract for all business value objects.
 * Provides a typed accessor to the underlying primitive value.
 *
 * @param <T> the underlying primitive type (e.g., String, Double, Boolean)
 */
public interface ValueObject<T> {

    T getValue();
}