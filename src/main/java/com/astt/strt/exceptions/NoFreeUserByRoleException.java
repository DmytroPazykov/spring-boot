package com.astt.strt.exceptions;

import com.astt.strt.data.model.Role;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

@Slf4j
@Getter
public class NoFreeUserByRoleException extends NoSuchElementException {

    private String error;

    public NoFreeUserByRoleException(Role role) {
        this.error = String.format("Could not found any free user by role '%s'", role);
        log.error(error);
    }
}
