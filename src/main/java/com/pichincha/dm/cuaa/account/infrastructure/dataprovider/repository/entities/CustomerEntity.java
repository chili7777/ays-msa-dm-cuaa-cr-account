package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

public record CustomerEntity(String id,
                             String identification,
                             String fullName,
                             String gender,
                             Integer age,
                             String email,
                             String phone,
                             String address,
                             String password,
                             Boolean status) {
}