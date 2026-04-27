package com.donation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "Backend Running 🚀";
    }

    @GetMapping("/test")
    public String test2() {
        return "Test endpoint working 🚀";
    }
}