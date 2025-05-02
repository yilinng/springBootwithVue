package com.practice.mysql.bookapi.security;

import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

//import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtTokenProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value("${app.jwt-secret-access}")
  private String jwtSecretAcc;

  @Value("${app.jwt-secret-refresh}")
  private String jwtSecretRefresh;

  @Value("${app-jwt-expiration-milliseconds}")
  private long jwtExpirationDate;

  // generate JWT token
  public Map<String, String> generateToken(Authentication authentication) {
    String username = authentication.getName();

    Date currentDate = new Date();

    Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

    // Initialization of a Map , using Generics
    Map<String, String> tokenMap = new HashMap<String, String>();

    // vscode signWith red line error,
    // https://stackoverflow.com/questions/73576686/what-substitute-can-i-use-for-java-springs-jwts-signwith-deprecated-method
    String accessToken = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS256, jwtSecretAcc)
        .compact();

    String refreshToken = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS256, jwtSecretRefresh)
        .compact();

    tokenMap.put("accessToken", accessToken);
    tokenMap.put("refreshToken", refreshToken);

    return tokenMap;
  }

  /*
   * private Key key(String token) {
   * return Keys.hmacShaKeyFor(
   * Decoders.BASE64.decode(token));
   * }
   */
  // get username from Jwt token
  public String getUsername(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecretAcc)
        .parseClaimsJws(token)
        .getBody();
    String username = claims.getSubject();
    return username;
  }

  // validate Jwt token
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(jwtSecretAcc)
          .parse(token);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
