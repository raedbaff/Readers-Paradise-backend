package com.example.books.payload.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewResponse {
    private Long id;
    private Float rating;
    private String comment;
    private String book;
    private String user;

}
