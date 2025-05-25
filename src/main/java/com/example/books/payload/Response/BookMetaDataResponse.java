package com.example.books.payload.Response;

import com.example.books.models.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookMetaDataResponse {
    private Long id;
    private String title;
    private String author;
    private String description;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Float borrowPrice;
    private Float buyPrice;
    private String coverPhoto;
    private String language;
    private Integer publishYear;
    private Integer totalPages;
    private boolean Free;
}
