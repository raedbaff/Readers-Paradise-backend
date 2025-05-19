package com.example.books.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")

public class StatusController {
    @RequestMapping("/")
    public String getStatus() {
        return "Server is running!";
    }

}
