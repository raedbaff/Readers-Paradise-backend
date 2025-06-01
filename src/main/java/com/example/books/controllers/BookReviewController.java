package com.example.books.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.books.models.BookReview;
import com.example.books.models.User;
import com.example.books.payload.Request.BookReviewRequest;
import com.example.books.services.BookReviewService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reviews")
public class BookReviewController {
    @Autowired
    private BookReviewService bookReviewService;
    @PostMapping("/")
    public ResponseEntity<?> createReview(@Valid @RequestBody BookReviewRequest review, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BookReview newReview = bookReviewService.createReview(review.getBookId(),user, review.getRating(), review.getComment());
        HashMap<String, Object> entity = new HashMap<>();
        entity.put("message", "Review created successfully");
        entity.put("rating", newReview.getRating());
        entity.put("comment", newReview.getComment());
        entity.put("userId", newReview.getUser().getId());
        entity.put("bookId", review.getBookId());
    
        return ResponseEntity.ok().body(entity);
    }
    

}
