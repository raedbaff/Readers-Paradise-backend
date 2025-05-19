package com.example.books.payload.Request;

import com.example.books.models.Genre;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class CreateBookRequest {
    @Size(min = 2, max = 100, message = "Title must be at least 2 characters long and at most 100 characters long")
    private String title;
    @Size(min = 2, max = 100, message = "Title must be at least 2 characters long and at most 100 characters long")
    private String author;
    @Size(min = 2, max = 100, message = "Title must be at least 2 characters long and at most 100 characters long")
    private String description;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Lob
    private String content;
    private Float borrowPrice;
    private Float buyPrice;
    private String coverPhoto;
    @Size(min = 2, max = 100, message = "Title must be at least 2 characters long and at most 100 characters long")
    private String language;
    private Integer publishYear;
    private Integer totalPages;
    private boolean Free;
}
