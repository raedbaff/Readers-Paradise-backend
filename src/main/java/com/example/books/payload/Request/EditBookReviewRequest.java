package com.example.books.payload.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class EditBookReviewRequest {
    @Max(5)
    @Min(1)
    @NotNull(message = "Rating is required")
    private Float rating;
    @Size(max = 500, min = 4, message = "Comment must be between 4 and 500 characters")
    @NotNull(message = "Comment is required")
    private String comment;


}
