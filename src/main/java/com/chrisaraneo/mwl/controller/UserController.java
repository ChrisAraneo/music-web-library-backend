package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.AppException;
import com.chrisaraneo.mwl.model.Role;
import com.chrisaraneo.mwl.model.RoleName;
import com.chrisaraneo.mwl.model.User;
import com.chrisaraneo.mwl.payload.ApiResponse;
import com.chrisaraneo.mwl.payload.JwtAuthenticationResponse;
import com.chrisaraneo.mwl.payload.LoginRequest;
import com.chrisaraneo.mwl.payload.SignUpRequest;
import com.chrisaraneo.mwl.repository.RoleRepository;
import com.chrisaraneo.mwl.repository.UserRepository;
import com.chrisaraneo.mwl.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        Optional<User> user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if(user.isPresent()) {
        	Set<Role> roles = user.get().getRoles();
        	return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, roles));
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping("/users")
    @Secured("ROLE_ADMIN")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
//    @GetMapping("/users/{id}")
//    public User getUserById(@PathVariable(value = "id") Integer userID) {
//        return userRepository.findById(userID)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
//    }
//
//    @PostMapping("/users")
//    @Secured("ROLE_ADMIN")
//    public User createUser(@Valid User user) {
//        return userRepository.save(user);
//    }

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