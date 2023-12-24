package com.company.repository;

import com.company.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from users u where u.username = ?1")
    Optional<User> findByUsername(String username);
}