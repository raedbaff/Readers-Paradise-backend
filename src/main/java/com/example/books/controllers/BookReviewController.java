package com.example.books.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.books.models.BookReview;
import com.example.books.models.User;
import com.example.books.payload.Request.BookReviewRequest;
import com.example.books.payload.Request.EditBookReviewRequest;
import com.example.books.payload.Response.BookReviewResponse;
import com.example.books.services.BookReviewService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




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
    @PutMapping("/{id}")
    public ResponseEntity<?> EditReview(@PathVariable Long id,@Valid @RequestBody EditBookReviewRequest review,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BookReview updatedReview = bookReviewService.editReview(id, user, review.getRating(), review.getComment());
        HashMap<String, Object> entity = new HashMap<>();
        entity.put("message", "Review updated successfully");
        entity.put("rating", updatedReview.getRating());
        entity.put("comment", updatedReview.getComment());

        return ResponseEntity.ok().body(entity);
    }
    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookReviews(@PathVariable Long bookId) {
        List<BookReviewResponse> mappedReviews = new ArrayList<>();
        List<BookReview> bookReviews = bookReviewService.getReviewsByBook(bookId);
        for (BookReview review : bookReviews) {
            BookReviewResponse response = new BookReviewResponse();
            response.setBook(review.getBook().getTitle());
            response.setComment(review.getComment());
            response.setRating(review.getRating());
            response.setUser(review.getUser().getUsername());
            response.setId(review.getId());
            mappedReviews.add(response);
        }
        return ResponseEntity.ok().body(mappedReviews);
    }
    
    

}
