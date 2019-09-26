package com.example.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user/details")
    public Authentication userDetails(Authentication authentication) {
        return authentication;
    }

    @Secured("ROLE_CABMONSOC_ADMIN")
    @GetMapping("/admin")
    public String admin() {
        return "allowed!";
    }
}
