package com.example.books.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String description;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Lob
    private String content;
    private Float borrowPrice;
    private Float buyPrice;
    private String coverPhoto;
    private String language;
    private Integer publishYear;
    private Integer totalPages;
    private boolean Free;
    @JsonIgnore
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<UserBook> userBooks;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookReview> reviews;

}
