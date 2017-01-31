package com.onrkrdmn.restapi.advice;

import com.onrkrdmn.service.exception.GameFullException;
import com.onrkrdmn.service.exception.GameNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by onur on 22.01.17.
 * Rest controller validation advice class
 * Handle {@link MethodArgumentNotValidException}
 * {@link GameFullException}
 */
@ControllerAdvice("com.onrkrdmn.restapi.controller")
public class ValidationErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors userNotFoundExceptionHandler(MethodArgumentNotValidException ex) {
        return new VndErrors("validation_error", errorMessage(ex.getBindingResult().getFieldError()));
    }

    @ResponseBody
    @ExceptionHandler(GameFullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors gameFullException(GameFullException ex) {
        return new VndErrors("validation_error", "The game has max 2 people");
    }

    @ResponseBody
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors gameNotFound(GameFullException ex) {
        return new VndErrors("validation_error", ex.getMessage());
    }

    private String errorMessage(FieldError fieldError) {
        return errorMessage(fieldError.getField(), fieldError.getRejectedValue());
    }

    private String errorMessage(String field, Object value) {

        return String.format("Field [%s] validation failed : rejected value [%s]",
                field, value);

    }
}
