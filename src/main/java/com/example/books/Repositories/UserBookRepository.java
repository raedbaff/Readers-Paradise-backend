package com.example.books.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.books.models.Book;
import com.example.books.models.User;
import com.example.books.models.UserBook;
@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    Optional<UserBook> findByUserAndBook(User user,Book book);
}
