package com.krzypio.pigment.controller;

import com.krzypio.pigment.exception.user.UserAlreadyExistException;
import com.krzypio.pigment.service.UserService;
import com.krzypio.pigment.entity.User;
import com.krzypio.pigment.exception.user.UserNotFoundException;
import com.krzypio.pigment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/myUser")
    public User getMyUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).get();
        return user;
    }

    @PatchMapping("/myUserPassword")
    public User updateMyUserPassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        User updatedUser = userService.updatePassword(oldPassword, newPassword);
        return updatedUser;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUserById(@PathVariable long id){
        Optional<User> retrievedUser = userRepository.findById(id);
        retrievedUser.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found.")); //to implement
        return retrievedUser.get();
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody User user){
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent())
            throw new UserAlreadyExistException("User with username: " + user.getUsername() + " already exist.");

        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();

//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(location);
//        ResponseEntity responseEntity = new ResponseEntity<User>(user, headers, HttpStatus.CREATED);

        return  ResponseEntity.created(location).body(savedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteById(@PathVariable long id){
        Optional<User> existingUser = userRepository.findById(id);
        existingUser.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
        userRepository.deleteById(id);
        return ResponseEntity.ok("User with id " + id + " deleted.");
    }
}

