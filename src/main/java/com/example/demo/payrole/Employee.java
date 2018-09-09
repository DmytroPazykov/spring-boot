package com.example.demo.payrole;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static java.util.Objects.isNull;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
public class Employee {

    private @Id
    @GeneratedValue
    BigDecimal id;

    private String name;
    private String role;

    Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public boolean equals(Object toCompareWith) {
        if (this == toCompareWith) {
            return true;
        }
        if (isNull(toCompareWith) || getClass() != toCompareWith.getClass()) {
            return false;
        }
        Employee employee = (Employee) toCompareWith;
        return Objects.equals(name, employee.name)
            && Objects.equals(role, employee.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, role);
    }
}
