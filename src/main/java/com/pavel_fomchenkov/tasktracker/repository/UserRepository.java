package com.pavel_fomchenkov.tasktracker.repository;

import com.pavel_fomchenkov.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
