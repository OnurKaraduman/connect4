package com.onrkrdmn.restapi.controller;

import com.onrkrdmn.restapi.resource.RootResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by onur on 22.01.17.
 * rest service controller as root
 */
@RestController
@RequestMapping("/")
public class RootRestController extends GameController {

    @RequestMapping(method = RequestMethod.GET)
    public RootResource getRoot() {
        return new RootResource();
    }

}
