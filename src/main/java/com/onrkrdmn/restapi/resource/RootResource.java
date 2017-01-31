package com.onrkrdmn.restapi.resource;

import com.onrkrdmn.restapi.controller.GameController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by onur on 28.01.17.
 * root resource
 */
public class RootResource extends ResourceSupport {

    private String message;

    public RootResource() {
        this.message = "Welcome to connect4 api version 1.0.0";
        add(
                linkTo(GameController.class).withRel("games")
        );

    }
}
