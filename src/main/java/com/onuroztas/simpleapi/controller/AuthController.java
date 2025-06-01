package com.onuroztas.simpleapi.controller;

import com.onuroztas.simpleapi.entity.User;
import com.onuroztas.simpleapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        
        User user = new User(username, passwordEncoder.encode(password));
        userRepository.save(user);
        
        return "User registered successfully: " + username;
    }
}