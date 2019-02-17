package com.astt.strt.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

@Slf4j
@Getter
public class NoSuchUserException extends NoSuchElementException {

    private String error;

    public NoSuchUserException(String userLogin){
        this.error = String.format("Could not found any user with such login '%s'", userLogin);
        log.error(error);
    }
}
