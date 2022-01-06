package com.krzypio.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/myAccount")
    public String getAccountDetails(String input){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Here are the account details from the DB for user: " + authentication.getName();
    }
}
