package com.example.books.payload.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ChangePasswordRequest {
    @NotBlank
    @Size(min = 8, max = 40, message = "Password must be at least 8 characters long and at most 40 characters long")
    private String oldPassword;
    @NotBlank
    @Size(min = 8, max = 40, message = "Password must be at least 8 characters long and at most 40 characters long")
    private String newPassword;

}
