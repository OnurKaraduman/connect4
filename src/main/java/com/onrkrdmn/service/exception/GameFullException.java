package com.onrkrdmn.service.exception;

/**
 * Created by onur on 28.01.17.
 * the exception is throw when the game doesn't has more place for player
 */
public class GameFullException extends RuntimeException {

    public GameFullException(String gameId) {
        super(String.format("Game %s is full.", gameId));
    }
}
