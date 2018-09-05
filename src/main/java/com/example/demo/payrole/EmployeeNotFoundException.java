package com.example.demo.payrole;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeNotFoundException extends NoSuchElementException {

    public EmployeeNotFoundException(BigDecimal id) {
        log.error("Could not found customer by id " + id);
    }
}
