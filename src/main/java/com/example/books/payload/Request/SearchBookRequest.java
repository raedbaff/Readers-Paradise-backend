package com.example.books.payload.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchBookRequest {
    @Size(max = 100, message = "Query must be less than 100 characters")
    @NotNull(message = "Query is required")
    private String query;

}
