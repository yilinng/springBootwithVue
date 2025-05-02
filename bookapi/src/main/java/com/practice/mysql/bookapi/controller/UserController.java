package com.practice.mysql.bookapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.practice.mysql.bookapi.dto.UserDto;
import com.practice.mysql.bookapi.dto.UserReservationsResponse;
import com.practice.mysql.bookapi.dto.UserResponse;
import com.practice.mysql.bookapi.dto.JWTAuthResponse;
import com.practice.mysql.bookapi.dto.LoginDto;
import com.practice.mysql.bookapi.dto.SignupDto;

import com.practice.mysql.bookapi.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  private UserService userService;

  // https://stackoverflow.com/questions/65522029/how-to-use-hasrole-in-spring-security-properly
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/users")
  public ResponseEntity<UserResponse> getUsers(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
    return new ResponseEntity<>(userService.getAllUsers(pageNo, pageSize), HttpStatus.OK);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserDto> userDetail(@PathVariable int id) {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
    JWTAuthResponse response = userService.login(loginDto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto) {
    return new ResponseEntity<>(userService.signup(signupDto), HttpStatus.CREATED);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<UserDto> updateUser(
      @RequestBody UserDto userDto, @PathVariable("id") int userId) {
    UserDto response = userService.updateUser(userDto, userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") int userId) {
    userService.deleteUserById(userId);
    return new ResponseEntity<>("User deleted", HttpStatus.OK);
  }

  @GetMapping("/users/{id}/reservations")
  public ResponseEntity<UserReservationsResponse> userReservations(@PathVariable int id) {
    return new ResponseEntity<>(userService.getUserReservations(id), HttpStatus.OK);
  }
}
