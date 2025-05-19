package com.example.books.payload.Request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateBookRequest {
    @Size(min = 2, max = 100, message = "Description must be at least 2 characters long and at most 100 characters long")
    private String description;
    private Float borrowPrice;
    private Float buyPrice;
    private String language;
    private boolean Free;
}
