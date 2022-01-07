package com.krzypio.springsecuritybasic.controller;

import com.krzypio.springsecuritybasic.entity.User;
import com.krzypio.springsecuritybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/myUser")
    public User getMyUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).get();
        return user;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUserById(@PathVariable long id){
        Optional<User> retrievedUser = userRepository.findById(id);
        //retrievedUser.orElseThrow(() -> new UserNotFoundException("Not found: " + id)); //to implement
        return retrievedUser.get();
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user){
        User savedUser = userRepository.save(user);
        //if (savedUser == null) throw new UserAlreadyExistException("User with username: " + user.getUsername() + " already exist.");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();

//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(location);
//        ResponseEntity responseEntity = new ResponseEntity<User>(user, headers, HttpStatus.CREATED);

        return  ResponseEntity.created(location).body(user);
    }
}

