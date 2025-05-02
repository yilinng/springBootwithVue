package com.practice.mysql.bookapi.security;

import org.springframework.security.core.Authentication;

//https://www.baeldung.com/get-user-in-spring-security
public interface IAuthenticationFacade {
  Authentication getAuthentication();
}
