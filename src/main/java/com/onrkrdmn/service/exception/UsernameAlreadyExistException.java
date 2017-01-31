package com.onrkrdmn.service.exception;

/**
 * Created by onur on 28.01.17.
 */
public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException(String userName) {
        super(userName + " already exist. Please use different username");
    }
}
