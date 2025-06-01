package com.onuroztas.simpleapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test-auth")
    public ResponseEntity<String> testAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return ResponseEntity.ok("You are authenticated as: " + auth.getName());
        } else {
            return ResponseEntity.status(401).body("Not authenticated");
        }
    }
} 