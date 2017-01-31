package com.onrkrdmn.restapi.exception;

/**
 * Created by onur on 24.01.17.
 * the exception is handled, if player is not exist
 */
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String userName) {
        super(String.format("User %s not found.", userName));
    }
}
