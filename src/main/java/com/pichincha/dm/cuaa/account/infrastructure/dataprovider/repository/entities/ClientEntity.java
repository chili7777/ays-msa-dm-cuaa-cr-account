package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class ClientEntity extends PersonEntity {
    @Column(nullable = false, unique = true, length = 20)
    private String identification;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private Boolean status;

    @Column(length = 20)
    private String role;

    public ClientEntity(String id, String fullName, String gender, Integer age, String email, String phone, String address, String identification, String password, Boolean status, String role) {
        super(id, fullName, gender, age, email, phone, address);
        this.identification = identification;
        this.password = password;
        this.status = status;
        this.role = role;
    }
}