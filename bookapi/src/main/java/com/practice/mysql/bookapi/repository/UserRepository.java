package com.practice.mysql.bookapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.mysql.bookapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
