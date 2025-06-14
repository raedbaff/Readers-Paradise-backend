package com.example.books.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.books.models.Book;
import com.example.books.payload.Response.SearchBook;
@Repository

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT new com.example.books.payload.Response.SearchBook(b.id, b.title, b.description, b.author, b.genre) from Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.genre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<SearchBook> searchBooks(@Param("query") String query);

}
