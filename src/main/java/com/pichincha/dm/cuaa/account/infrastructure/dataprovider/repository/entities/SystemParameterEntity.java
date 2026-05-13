package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "system_parameters")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemParameterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "parameter_value", nullable = false, length = 255)
    private String value;

    @Column(length = 255)
    private String description;
}