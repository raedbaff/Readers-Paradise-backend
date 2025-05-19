package com.example.books.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.books.exceptions.InvalidCredentialsException;
import com.example.books.models.User;
import com.example.books.payload.Request.ChangePasswordRequest;
import com.example.books.payload.Response.SuccessResponse;
import com.example.books.payload.Response.UploadMediaResponse;
import com.example.books.services.UserService;
import com.example.books.utils.UploadService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Validated

public class UserController {
    @Autowired
    UploadService uploadService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;

    @PutMapping("/uploadPhoto")
    public ResponseEntity<?> uploadPhoto(Authentication authentication, @RequestParam("file") MultipartFile file) {
        User user = (User) authentication.getPrincipal();
        String fileName;
        try {
            fileName = uploadService.upload(file);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload photo: " + e.getMessage());
        }
        user.setPhoto(fileName);
        userService.saveUser(user);

        return ResponseEntity.ok(new UploadMediaResponse("Photo uploaded successfully."));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {

            throw new InvalidCredentialsException("old password is incorrect");
        }
        if (encoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("new password cannot be the same as the old password");
        }
        user.setPassword(encoder.encode(request.getNewPassword()));
        userService.saveUser(user);

        return ResponseEntity.ok(new SuccessResponse("password changed successfully."));
    }
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.deleteUser(user.getId());
        return ResponseEntity.ok(new SuccessResponse("account deleted successfully."));
    }

}
