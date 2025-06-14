package com.example.books.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.books.models.BookReview;
@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    
    List<BookReview> findByBookId(Long bookId);
    List<BookReview> findByUserId(Long userId);
    Optional<BookReview> findByBookIdAndUserId(Long reviewId, Long userId);

}
