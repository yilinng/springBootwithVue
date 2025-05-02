package com.practice.mysql.bookapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.mysql.bookapi.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findRoleByName(String name);

  Boolean existsByName(String name);
}
