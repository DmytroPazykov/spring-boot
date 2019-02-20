package com.astt.strt.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.astt.strt.exceptions.NoSuchRoleException;

@ControllerAdvice
public class RoleNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoSuchRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String roleNotFoundHandler(NoSuchRoleException ex) {
        return ex.getError();
    }
}
