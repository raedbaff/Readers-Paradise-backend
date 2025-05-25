package com.example.books.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.books.Repositories.BookReviewRepository;
import com.example.books.exceptions.NotAllowedException;
import com.example.books.exceptions.ResourceAlreadyExistsException;
import com.example.books.exceptions.ResourceNotFoundException;
import com.example.books.models.Book;
import com.example.books.models.BookReview;
import com.example.books.models.User;

public class BookReviewService {
    @Autowired
    private BookReviewRepository bookReviewRepository;
    @Autowired
    private BookService bookService;

    BookReview createReview(Long bookId, User user, Float rating, String comment) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            throw new ResourceNotFoundException("The book you are trying to review does not exist");
        }
        Optional<BookReview> review = bookReviewRepository.findByBookIdAndUserId(bookId, user.getId());
        if (review.isPresent()) {
            throw new ResourceAlreadyExistsException("You have already reviewed this book");
        }
        BookReview bookReview = new BookReview();
        bookReview.setBook(book);
        bookReview.setUser(user);
        bookReview.setRating(rating);
        bookReview.setComment(comment);
        return bookReviewRepository.save(bookReview);

    }
    List<BookReview> getReviewsByBook(Long bookId) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            throw new ResourceNotFoundException("The book you are trying to get reviews for does not exist");
        }
        return bookReviewRepository.findByBookId(bookId);
    }
    BookReview editReview(Long reviewId, User user, Float rating, String comment) {
        Optional<BookReview> bookReview = bookReviewRepository.findById(reviewId);
        if (bookReview.isEmpty()) {
            throw new ResourceNotFoundException("The review you are trying to edit does not exist");
        }
        if (bookReview.get().getUser().getId() != user.getId()) {
            throw new NotAllowedException("You are not allowed to edit this review");
        }
        bookReview.get().setRating(rating);
        bookReview.get().setComment(comment);
        return bookReviewRepository.save(bookReview.get());
    }

}
