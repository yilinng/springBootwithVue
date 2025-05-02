package com.practice.mysql.bookapi.security;

import com.practice.mysql.bookapi.exception.UserNotFoundException;
import com.practice.mysql.bookapi.model.User;
import com.practice.mysql.bookapi.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    /*
     * https://stackoverflow.com/questions/26727812/spring-crudrepository-
     * orelsethrow
     */
    User user = userRepository.findByEmail(email);
    // .orElseThrow(() -> new CustomNotFoundException("User not found email: " +
    // email));

    if (user == null) {
      throw new UserNotFoundException("User not found email: " + email);

    }
    // System.out.println("userRepository.findByUsernameOrEmail(usernameOrEmail,
    // usernameOrEmail) "
    // + userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail));

    System.out.println("user.getRoles " + user.getRoles());

    Set<GrantedAuthority> authorities = user
        .getRoles()
        .stream()
        .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(),
        authorities);
  }
}
