package com.example.demo.payrole;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

interface EmployeeRepository extends JpaRepository<Employee, BigDecimal> {
}
