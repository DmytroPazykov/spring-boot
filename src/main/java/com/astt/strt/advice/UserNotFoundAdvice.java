package com.astt.strt.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.astt.strt.exceptions.NoSuchUserException;

public class UserNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(NoSuchUserException ex) {
        return ex.getError();
    }
}
