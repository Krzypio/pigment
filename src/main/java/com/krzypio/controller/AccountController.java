package com.krzypio.controller;

import com.krzypio.security.models.Customer;
import com.krzypio.security.models.User;
import com.krzypio.security.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public String getAccountDetails(String input){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email).get();
        return "Here are the account details from the DB for user: \n" + customer;
    }
}
