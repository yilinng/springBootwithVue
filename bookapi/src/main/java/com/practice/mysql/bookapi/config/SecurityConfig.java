package com.practice.mysql.bookapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.practice.mysql.bookapi.security.JwtAuthenticationEntryPoint;
import com.practice.mysql.bookapi.security.JwtAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

//https://stackoverflow.com/questions/74910066/enableglobalmethodsecurity-is-deprecated-in-the-new-spring-boot-3-0
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  // the session policy is set to STATELESS because we’ll use REST endpoints
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    // https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
    // https://stackoverflow.com/questions/64191637/the-method-withdefaults-is-undefined-for-the-type-securityconfiguration
    http.cors(withDefaults()).csrf(csrf -> {
      try {
        csrf.disable()
            .exceptionHandling(handling -> handling
                .authenticationEntryPoint(jwtAuthenticationEntryPoint));
      } catch (Exception e) {
        e.printStackTrace();
      }
    })
        .authorizeHttpRequests(rmr -> rmr
            // .requestMatchers("/api/admin/**").hasRole("Admin")
            // .requestMatchers("/api/customers/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/signup").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
            .anyRequest().authenticated())
        .sessionManagement(management -> management
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
