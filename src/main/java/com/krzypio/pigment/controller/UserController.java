package com.krzypio.pigment.controller;

import com.krzypio.pigment.entity.ProductionBatch;
import com.krzypio.pigment.exception.other.ProductionBatchNotFoundException;
import com.krzypio.pigment.exception.user.UserAlreadyExistException;
import com.krzypio.pigment.exception.user.UserPasswordNotMatchException;
import com.krzypio.pigment.service.UserService;
import com.krzypio.pigment.entity.User;
import com.krzypio.pigment.exception.user.UserNotFoundException;
import com.krzypio.pigment.repository.UserRepository;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/myUser")
    public User getMyUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).get();
        return user;
    }

    /**
     * It is only place to change password.
     * @param newUser need to specify new password, rest is not used but must be included (any value).
     * @param oldPassword used to compare with actual password in database.
     * @return informations about changed user.
     */
    @PutMapping("/myUserPassword")
    public ResponseEntity updateMyUserPassword( @Valid @RequestBody User newUser, @RequestParam String oldPassword){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> retrievedUser = userRepository.findByUsername(authentication.getName());
        retrievedUser.orElseThrow(() -> new UserNotFoundException("myUser not found."));

        if (!passwordEncoder.matches(oldPassword, retrievedUser.get().getPassword()))
            throw new UserPasswordNotMatchException("Old password doesn't match password in database.");

        User savedUser = retrievedUser.get();
        savedUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        ResponseEntity responseEntity = new ResponseEntity<User>(savedUser, headers, HttpStatus.OK);
        return responseEntity;
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

    /**
     * This is used to change Username, Active and Roles. Password is not get into consideration but must be added (anything). To change password use "updateMyUserPassword" method.
     * @param newUser
     * @param id
     * @return
     */
    @PutMapping("/users/{id}")
    public ResponseEntity replaceUser(@Valid @RequestBody User newUser, @PathVariable long id){
        return userRepository.findById(id)
                .map(u -> {
                    u.setUsername(newUser.getUsername());
                    u.setActive(newUser.isActive());
                    u.setRoles(newUser.getRoles());

                    User savedUser = userRepository.save(u);

                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}").buildAndExpand(savedUser.getId())
                            .toUri();

                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(location);
                    ResponseEntity responseEntity = new ResponseEntity<User>(savedUser, headers, HttpStatus.OK);

                    return  responseEntity;
                }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
    }
}

