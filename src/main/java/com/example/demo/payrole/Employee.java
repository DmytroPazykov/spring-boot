package com.example.demo.payrole;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
class Employee {

    private @Id
    @GeneratedValue
    BigDecimal id;

    private String name;
    private String role;

    Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }
}
