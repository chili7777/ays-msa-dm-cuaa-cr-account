package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonEntity {
    @Id
    @Column(length = 36, nullable = false)
    private String id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(length = 20)
    private String gender;

    private Integer age;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 200)
    private String address;
}