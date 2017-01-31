package com.onrkrdmn.restapi.advice;

import com.onrkrdmn.restapi.exception.PlayerNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by onur on 22.01.17.
 * Rest controller advice class
 * Handle {@link PlayerNotFoundException}
 */
@ControllerAdvice("com.onrkrdmn.restapi.controller")
public class RestControllerAdvice {

    @ResponseBody
    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors userNotFoundExceptionHandler(PlayerNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

}
