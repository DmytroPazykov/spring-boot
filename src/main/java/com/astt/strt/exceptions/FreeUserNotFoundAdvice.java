package com.astt.strt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class FreeUserNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoFreeUserByRoleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String freeUserNotFoundHandler(NoFreeUserByRoleException ex) {
        return ex.getError();
    }
}
