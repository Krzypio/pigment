package com.krzypio.springsecuritybasic.controller;

import com.krzypio.springsecuritybasic.entity.User;
import com.krzypio.springsecuritybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/myAccount")
    public String getAccountDetails(String input){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByUsername(email).get();
        return "Here are the account details from the DB for user: \n" + user;
    }
}
