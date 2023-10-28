package org.dontbelate.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/userservice/public")
public class DBLUserControllerPublic {

    @GetMapping("hello")
    public String hello() {
        return "Hello from Spring boot & Keycloak";
    }
}
