package com.onrkrdmn.service.exception;

/**
 * Created by onur on 28.01.17.
 * If the game couldnt be found, then the exception is throw
 */
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String gameId) {
        super(String.format("Game %s not found.", gameId));
    }
}
