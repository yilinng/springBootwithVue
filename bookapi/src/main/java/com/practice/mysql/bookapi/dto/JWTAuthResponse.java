package com.practice.mysql.bookapi.dto;

import lombok.Data;

//https://stackoverflow.com/questions/48238626/why-would-a-restful-api-send-cookies-with-the-api-response
@Data
public class JWTAuthResponse {
  private String accessToken;

  private String refreshToken;

  private String message;

  private String tokenType = "Bearer";

  private String userId;
}
