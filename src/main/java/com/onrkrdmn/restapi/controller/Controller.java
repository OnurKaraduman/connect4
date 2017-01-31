package com.onrkrdmn.restapi.controller;

/**
 * Created by onur on 23.01.17.
 * interface for rest controllers
 */
public interface Controller {

    public static final String CLIENT = "hasRole('ROLE_CLIENT')";
    public static final String ADMIN = "hasRole('ROLE_ADMIN')";
}
