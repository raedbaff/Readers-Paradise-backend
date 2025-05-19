package com.example.books.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.books.models.User;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    
    // Get all users paginated

}
