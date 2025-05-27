package com.onuroztas.simpleapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Simple API!";
    }
} 