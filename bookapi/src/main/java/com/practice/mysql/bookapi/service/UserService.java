package com.practice.mysql.bookapi.service;

import com.practice.mysql.bookapi.dto.UserDto;
import com.practice.mysql.bookapi.dto.UserReservationsResponse;
import com.practice.mysql.bookapi.dto.UserResponse;
import com.practice.mysql.bookapi.dto.JWTAuthResponse;
import com.practice.mysql.bookapi.dto.LoginDto;
import com.practice.mysql.bookapi.dto.SignupDto;

public interface UserService {

  UserResponse getAllUsers(int pageNo, int pageSize);

  UserDto signup(SignupDto signupDto);

  JWTAuthResponse login(LoginDto loginDto);

  UserDto getUserById(int userId);

  UserDto updateUser(UserDto userDto, int id);

  void deleteUserById(int userId);

  UserReservationsResponse getUserReservations(int id);

}
