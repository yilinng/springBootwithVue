package com.practice.mysql.bookapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.mysql.bookapi.model.Role;
import com.practice.mysql.bookapi.repository.RoleRepository;
import com.practice.mysql.bookapi.service.RoleService;

//import jakarta.annotation.PostConstruct;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role findByName(String name) {
    // Find role by name using the roleDao
    Role role = roleRepository.findRoleByName(name);
    return role;
  }

  @Override
  public Role AddRole(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public Boolean isExist(String name) {
    return roleRepository.existsByName(name);
  }
  /*
   * @Override
   * public void initDb() {
   * roleRepository.deleteAll();
   * }
   */
}
