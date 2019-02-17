package com.astt.strt.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

@Slf4j
@Getter
public class NoSuchRoleException extends NoSuchElementException {

    private String error;

    public NoSuchRoleException(String role) {
        this.error = String.format("Could not found any role '%s'", role);
        log.error(error);
    }
}
