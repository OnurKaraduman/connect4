package com.onrkrdmn.restapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by onur on 24.01.17.
 * Generic response entity for rest web service
 */
@Getter
@Setter
public class Response<T> extends ResponseEntity<T> {
    private int errorCode;
    private String message;

    public Response(T body, HttpStatus status) {
        super(body, status);
        this.errorCode = status.value();
        this.message = "Success";
    }

    public Response() {
        super(HttpStatus.OK);
        this.errorCode = HttpStatus.OK.value();
        this.message = "Success";
    }

    public Response(String message, HttpStatus status) {
        super(status);
        this.errorCode = status.value();
        this.message = message;
    }
}
