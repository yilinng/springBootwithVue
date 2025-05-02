package com.practice.mysql.bookapi.dto;

import lombok.Data;

@Data
public class LoginDto {
  private String email;
  private String password;
}
