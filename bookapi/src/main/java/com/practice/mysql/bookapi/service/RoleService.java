package com.practice.mysql.bookapi.service;

import com.practice.mysql.bookapi.model.Role;

public interface RoleService {
  // Method to find a Role by its name
  Role findByName(String name);

  Role AddRole(Role role);

  Boolean isExist(String name);

  // void initDb();
}
