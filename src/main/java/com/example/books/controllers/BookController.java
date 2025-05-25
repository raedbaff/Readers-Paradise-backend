package com.example.books.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.books.mappers.BookMapper;
import com.example.books.models.Book;
import com.example.books.models.User;
import com.example.books.models.UserBook;
import com.example.books.payload.Request.CreateBookRequest;
import com.example.books.payload.Response.BookMetaDataResponse;
import com.example.books.payload.Response.CreateBookResponse;
import com.example.books.services.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Book book = bookService.getAccessibleBook(id, user);

        return ResponseEntity.ok().body(book);
    }

    @GetMapping("/book-details/{id}")
    public ResponseEntity<BookMetaDataResponse> getBookDetails(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.ok().body(BookMapper.toBookMetaDataResponse(book));
    }

    @PostMapping("/")
    public ResponseEntity<?> createBook(@Valid @RequestBody CreateBookRequest book) {
        Book newBook = Book.builder().title(book.getTitle()).author(book.getAuthor()).description(book.getDescription())
                .genre(book.getGenre()).content(book.getContent()).borrowPrice(book.getBorrowPrice())
                .buyPrice(book.getBuyPrice()).language(book.getLanguage()).publishYear(book.getPublishYear())
                .totalPages(book.getTotalPages()).Free(book.isFree()).build();
        bookService.createBook(newBook);
        return ResponseEntity.ok().body(new CreateBookResponse(book.getTitle(), book.getDescription()));

    }

    @PostMapping("/borrow/{id}")
    public ResponseEntity<?> borrowBook(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        UserBook userBook = bookService.borrowBook(user, id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book borrowed successfully");
        response.put("book", userBook.getBook().getTitle());

        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/buy/{id}")
    public ResponseEntity<?> buyBook(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        UserBook userBook = bookService.buyBook(user, id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book bought successfully");
        response.put("book", userBook.getBook().getTitle());

        return ResponseEntity.ok().body(response);
    }

}
