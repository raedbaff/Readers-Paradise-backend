package com.example.books.payload.Response;

import com.example.books.models.Genre;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor 
@Getter
@Setter
public class SearchBook {
    private Long id;
    private String title;
    private String author;
    private String description;
    private Genre genre;

    public SearchBook(Long id, String title,String description, String author, Genre genre) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.genre = genre;
    }

}
