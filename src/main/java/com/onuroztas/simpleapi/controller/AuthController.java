package com.onuroztas.simpleapi.controller;

import com.onuroztas.simpleapi.entity.User;
import com.onuroztas.simpleapi.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest, HttpServletRequest request) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(passwordEncoder.matches(password, user.getPassword())) {
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, List.of());

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", context);

            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> registerRequest) {
        String email = registerRequest.get("email");
        String password = registerRequest.get("password");

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(email, hashedPassword);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

}