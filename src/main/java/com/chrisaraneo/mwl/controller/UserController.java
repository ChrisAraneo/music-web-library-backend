package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.User;
import com.chrisaraneo.mwl.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
//    @GetMapping("/users/{id}")
//    public User getUserById(@PathVariable(value = "id") Integer userID) {
//        return userRepository.findById(userID)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
//    }

    @PostMapping("/users")
    public User createUser(@Valid User user) {
        return userRepository.save(user);
    }

//    @PutMapping("/users/{id}")
//    public User updateUser(@PathVariable(value = "id") Integer userID,
//                                           @Valid User modified) {
//
//        User user = userRepository.findById(userID)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
//
//        user.setBanned(modified.getBanned());
//        user.setCurrentConnections(modified.getCurrentConnections());
//        user.setEmail(modified.getEmail());
//        user.setName(modified.getName());
//        user.setPassword(modified.getPassword());
//        user.setResetCode(modified.getResetCode());
//        user.setResetDate(modified.getResetDate());
//        user.setSuperUser(modified.getSuperUser());
//        user.setTotalConnections(modified.getTotalConnections());
//        user.setUser(modified.getUser());
//
//        return userRepository.save(user);
//    }

//    @DeleteMapping("/users/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Integer userID) {
//        User user = userRepository.findById(userID)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
//
//        userRepository.delete(user);
//
//        return ResponseEntity.ok().build();
//    }
}