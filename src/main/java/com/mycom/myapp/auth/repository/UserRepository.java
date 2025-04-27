package com.mycom.myapp.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
 * Retrieves a user entity by its email address.
 *
 * @param userEmail the email address to search for
 * @return an Optional containing the user if found, or empty if no user exists with the given email
 */
Optional<User> findByUserEmail(String userEmail);
}
