package com.example.books.services;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.books.Repositories.UserRepository;
import com.example.books.models.User;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);

    }
    public List<User> getAllUsers(Integer pageNumber, Integer pageSize) {
        Pageable firstPageWithTwoElements = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(firstPageWithTwoElements).getContent();
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }

}
