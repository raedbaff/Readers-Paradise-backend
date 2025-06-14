package com.example.books.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.books.Repositories.BookRepository;
import com.example.books.Repositories.UserBookRepository;
import com.example.books.exceptions.DataConflictException;
import com.example.books.exceptions.NotAllowedException;
import com.example.books.exceptions.ResourceNotFoundException;
import com.example.books.models.Book;
import com.example.books.models.User;
import com.example.books.models.UserBook;
import com.example.books.payload.Request.UpdateBookRequest;
import com.example.books.payload.Response.SearchBook;

@Service

public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserBookRepository userBookRepository;

    public Book getBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null)
            throw new ResourceNotFoundException("The book you are looking for does not exist");
        return bookRepository.findById(id).orElse(null);
    }

    public Book getAccessibleBook(Long id, User user) {
        Book book = getBook(id);
        if (book == null)
            throw new ResourceNotFoundException("The book you are looking for does not exist");
        if (book.isFree())
            return book;
        UserBook userBook = getByUserAndBook(user, book);
        if (userBook != null &&  userBook.getRetunDate() != null && userBook.getRetunDate().isBefore(LocalDate.now())) {
            expireBorrow(userBook.getId());
            throw new NotAllowedException("Borrow period expired");
        }

        if (userBook == null || (!userBook.isBorrowed() && !userBook.isBought())) {
            throw new NotAllowedException("You don't own or have access to this book");
        }
        return book;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, UpdateBookRequest book) {
        Book existingBook = getBook(id);
        if (existingBook != null) {
            existingBook.setDescription(book.getDescription());
            existingBook.setBorrowPrice(book.getBorrowPrice());
            existingBook.setBuyPrice(book.getBuyPrice());
            existingBook.setLanguage(book.getLanguage());
            existingBook.setFree(book.isFree());
            return bookRepository.save(existingBook);
        }
        return null;

    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public UserBook getUserbook(Long id) {
        return userBookRepository.findById(id).orElse(null);
    }

    public UserBook getByUserAndBook(User user, Book book) {
        Optional<UserBook> userBook = userBookRepository.findByUserAndBook(user, book);
        if (userBook.isPresent()) {
            return userBook.get();
        }
        return null;
    }
    public List<SearchBook> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

    public UserBook borrowBook(User user, Long id) {
        Book book = getBook(id);
        if (book == null)
            throw new ResourceNotFoundException("The book you are looking for does not exist");
        UserBook existingUserBook = getByUserAndBook(user, book);
        if (existingUserBook != null && existingUserBook.isBorrowed())
            throw new DataConflictException("You have already borrowed this book");
        if (existingUserBook != null && !existingUserBook.isBorrowed()) {
            UserBook newBorrow = renewBorrow(existingUserBook.getId());
            return newBorrow;
        }
        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setBorrowed(true);
        userBook.setBorrowDate(LocalDate.now());
        userBook.setRetunDate(LocalDate.now().plusDays(30));
        return userBookRepository.save(userBook);

    }

    public UserBook buyBook(User user, Long id) {
        Book book = getBook(id);
        if (book == null)
            throw new ResourceNotFoundException("The book you are looking for does not exist");
        UserBook existingUserBook = getByUserAndBook(user, book);
        if (existingUserBook != null && existingUserBook.isBought())
            throw new DataConflictException("You have already bought this book");
        if (existingUserBook != null && existingUserBook.isBorrowed()) {
            existingUserBook.setBought(true);
            return userBookRepository.save(existingUserBook);
        } else {
            UserBook userBook = new UserBook();
            userBook.setUser(user);
            userBook.setBook(book);
            userBook.setBought(true);
            return userBookRepository.save(userBook);
        }

    }

    public UserBook expireBorrow(Long id) {
        UserBook userBook = getUserbook(id);
        if (userBook != null) {
            userBook.setBorrowed(false);
            return userBookRepository.save(userBook);
        }
        return null;

    }

    public UserBook renewBorrow(Long id) {
        UserBook userBook = getUserbook(id);
        if (userBook != null) {
            userBook.setBorrowed(true);
            userBook.setBorrowDate(LocalDate.now());
            userBook.setRetunDate(LocalDate.now().plusDays(30));
            return userBookRepository.save(userBook);
        }
        return null;

    }

}
