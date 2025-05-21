package com.example.books.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.books.Repositories.UserRepository;
import com.example.books.exceptions.ResourceAlreadyExistsException;
import com.example.books.models.User;
import com.example.books.payload.LoginResponse;
import com.example.books.payload.Request.LoginRequest;
import com.example.books.payload.Request.RegisterRequest;
import com.example.books.security.JWTUtils;
import com.example.books.services.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;



@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    JWTUtils jwt;
    @Autowired
    AuthenticationManager authenticationManager;
  

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest data) {
        Optional<User> userByEmail = userRepository.getUserByEmail(data.getEmail());
        Optional<User> userByUsername = userRepository.getUserByUsername(data.getUsername());
        if (userByEmail.isPresent()) {
            throw new ResourceAlreadyExistsException("user by the email " + data.getEmail() + " already exists");
        }
        if (userByUsername.isPresent()) {
            throw new ResourceAlreadyExistsException("user by the username " + data.getUsername() + " already exists");
        }
        User user = new User();
        user.setEmail(data.getEmail());
        user.setUsername(data.getUsername());
        user.setPassword(encoder.encode(data.getPassword()));
        return ResponseEntity.ok(userService.saveUser(user));

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwt.generateToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new LoginResponse(token,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }
 

}
