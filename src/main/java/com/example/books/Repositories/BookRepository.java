package com.example.books.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.books.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
